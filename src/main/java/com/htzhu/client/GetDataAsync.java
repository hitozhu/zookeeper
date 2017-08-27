package com.htzhu.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by htzhu on 2017/8/26.
 */
public class GetDataAsync implements Watcher {

    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * ip:port timeout watch
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetDataAsync());
        System.err.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {

        System.err.println(event);

        // 已链接
        if (event.getState() == Event.KeeperState.SyncConnected) {

            if (event.getType() == Event.EventType.None && null == event.getPath()) {
                eventProcess();
            } else {
                if (event.getType() == Event.EventType.NodeDataChanged) {
                    zooKeeper.getData("/node1", true, new IDataCallback(), null);

                }
            }
        }

    }

    /**
     * 节点数据
     */
    private void eventProcess() {

        zooKeeper.getData("/node1", true, new IDataCallback(), null);

    }

    static class IDataCallback implements AsyncCallback.DataCallback {

        public void processResult(int i, String path, Object o, byte[] bytes, Stat stat) {
            System.err.println(i);
            System.err.println(path);
            System.err.println(o);
            System.err.println(new String(bytes));
            System.err.println(stat.toString());
        }
    }

}
