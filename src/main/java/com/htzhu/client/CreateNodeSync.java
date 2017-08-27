package com.htzhu.client;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by htzhu on 2017/8/26.
 */
public class CreateNodeSync implements Watcher{

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * ip:port timeout watch
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeSync());
        System.err.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {

        System.err.println(event);

        // 已链接
        if (event.getState() == Event.KeeperState.SyncConnected) {
            eventProcess();
        }

    }

    /**
     * 创建节点
     */
    private void eventProcess() {

        try {

            /**
             * 节点路径 值 权限 创建模式（持久、临时）
             */
            String path = zooKeeper.create("/node1", "123456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            System.err.println(path);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
