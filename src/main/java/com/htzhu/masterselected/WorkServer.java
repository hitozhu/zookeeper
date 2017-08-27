package com.htzhu.masterselected;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by htzhu on 2017/8/27.
 */
public class WorkServer {

    /**
     * 服务状态
     */
    private volatile boolean running = false;

    private ZkClient zkClient;

    /**
     * master 节点路径
     */
    private static final String MASTER_PATH = "/master";

    /**
     * 数据监听
     */
    private IZkDataListener dataListener;

    /**
     * 当前服务信息
     */
    private RunningData serverData;

    /**
     * master 节点信息
     */
    private RunningData masterData;

    private ScheduledExecutorService delayExecutor = Executors.newScheduledThreadPool(1);

    /**
     * 延迟时间
     */
    private int delayTime = 5;

    public WorkServer(RunningData data, ZkClient zkClient) {
        this.serverData = data;
        this.zkClient = zkClient;
        // 监听
        dataListener = new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
            }

            public void handleDataDeleted(String s) throws Exception {

                // 上个master节点是自己，优先参与选举
                if (masterData != null && masterData.getName().equals(serverData.getName())) {
                    takeMaster();
                } else {
                    delayExecutor.schedule(new Runnable() {
                        public void run() {
                            takeMaster();
                        }
                    }, delayTime, TimeUnit.SECONDS);
                }
            }
        };
    }

    /**
     * 启动服务
     *
     * @throws Exception
     */
    public void start() throws Exception {
        if (running) {
            throw new Exception("server is running");
        }
        running = true;
        // 订阅 master 节点删除事件
        zkClient.subscribeDataChanges(MASTER_PATH, dataListener);
        // 争夺master
        takeMaster();
    }

    /**
     * 停止服务
     */
    public void stop() throws Exception {

        if (!running) {
            throw new Exception("server is stoped");
        }
        running = false;
        // 取消节点删除订阅
        zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);

        // 释放master
        releaseMaster();
    }

    /**
     * 参与master选举
     */
    private void takeMaster() {
        if (!running) {
            return;
        }
        try {
            zkClient.create(MASTER_PATH, serverData, CreateMode.EPHEMERAL);
            masterData = serverData;

            // 定制释放master权利
            delayExecutor.schedule(new Runnable() {
                public void run() {
                    if (checkMaster()) {
                        releaseMaster();
                    }
                }
            }, delayTime, TimeUnit.SECONDS);

        } catch (ZkNodeExistsException e) {
            // master 节点存在，读取master节点信息
            masterData = zkClient.readData(MASTER_PATH, true);
            // 读取过程中master失效，重新参加选举
            if (masterData == null) {
                takeMaster();
            }
        }
        if (masterData.getName().equals(serverData.getName())) {
            System.err.println("master is " + masterData.getName());
        }
    }

    /**
     * 释放master
     */
    private void releaseMaster() {

        if (checkMaster()) {
            zkClient.delete(MASTER_PATH);
        }
    }

    /**
     * 检查释放为master节点
     *
     * @return
     */
    private boolean checkMaster() {
        try {
            masterData = zkClient.readData(MASTER_PATH);
            if (masterData != null && masterData.getName().equals(serverData.getName())) {
                return true;
            }
            return false;
        } catch (ZkNoNodeException e) {
            // 节点不存在
            return false;
        } catch (ZkInterruptedException e) {
            return checkMaster();
        } catch (Exception e) {
            return false;
        }
    }

}
