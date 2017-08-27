package com.htzhu.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * Created by htzhu on 2017/8/26.
 */
public class GetChildren {

    public static void main(String[] args) {
        ZkClient client = new ZkClient("127.0.0.1:2181", 5000, 5000, new SerializableSerializer());
        System.err.println(client);

        List<String> nodes = client.getChildren("/");
        System.err.println(nodes);

    }

}
