package com.htzhu.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * Created by htzhu on 2017/8/26.
 */
public class SubscribeChildChange {

    public static void main(String[] args) throws InterruptedException {
        ZkClient client = new ZkClient("127.0.0.1:2181", 5000, 5000, new SerializableSerializer());
        System.err.println(client);

        client.subscribeChildChanges("/node_1", new ZkChildListener());

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static class ZkChildListener implements IZkChildListener {
        public void handleChildChange(String s, List<String> list) throws Exception {
            System.out.println(s);
            System.out.println(list.toString());
        }
    }

}
