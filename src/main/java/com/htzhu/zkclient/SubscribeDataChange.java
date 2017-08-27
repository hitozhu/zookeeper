package com.htzhu.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * Created by htzhu on 2017/8/26.
 */
public class SubscribeDataChange {

    public static void main(String[] args) throws InterruptedException {
        ZkClient client = new ZkClient("127.0.0.1:2181", 5000, 5000, new BytesPushThroughSerializer());
        System.err.println(client);

        client.subscribeDataChanges("/node_1", new ZkDataListener());

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static class ZkDataListener implements IZkDataListener {
        public void handleDataChange(String s, Object o) throws Exception {
            System.out.println(s);
            System.out.println(o);
        }

        public void handleDataDeleted(String s) throws Exception {
            System.out.println(s);
        }
    }

}
