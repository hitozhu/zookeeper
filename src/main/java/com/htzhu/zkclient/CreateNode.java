package com.htzhu.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

/**
 * Created by htzhu on 2017/8/26.
 */
public class CreateNode {

    public static void main(String[] args) {
        ZkClient client = new ZkClient("127.0.0.1:2181", 5000, 5000, new SerializableSerializer());
        System.err.println(client);

        User user = new User(1, "张三");
        String str = client.create("/node_1", user, CreateMode.PERSISTENT);

        System.err.println(str);
    }

}
