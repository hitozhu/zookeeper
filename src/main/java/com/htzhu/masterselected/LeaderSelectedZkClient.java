package com.htzhu.masterselected;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by htzhu on 2017/8/27.
 */
public class LeaderSelectedZkClient {

    // 服务个数
    private static final int CLIENT_NUM = 10;

    // zookeeper 服务器地址
    private static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";

    public static void main(String[] args) {

        // 所有服务端信息
        List<WorkServer> workServers = new ArrayList<WorkServer>();

        try {
            ZkClient zkClient = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new SerializableSerializer());
            // 循环创建客户端和服务端
            for (int i = 0; i < CLIENT_NUM; i++) {
                RunningData data = new RunningData(Long.valueOf(i), "client #" + i);
                WorkServer workServer = new WorkServer(data, zkClient);
                workServers.add(workServer);
                workServer.start();
            }

            System.err.println("输入回车键退出\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.err.println("shutting down");

            for (WorkServer workServer : workServers) {
                try {
                    workServer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
