package com.htzhu.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by htzhu on 2017/8/26.
 */
public class NodeExistsAsync implements Watcher {

    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * ip:port timeout watch
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new NodeExistsAsync());
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
                if (event.getType() == Event.EventType.NodeCreated) {
                    System.err.println(event.getPath() + " create");
                } else if (event.getType() == Event.EventType.NodeDataChanged) {
                    System.err.println(event.getPath() + " data change");
                } else if (event.getType() == Event.EventType.NodeDeleted) {
                    System.err.println(event.getPath() + " node delete");
                }

                zooKeeper.exists(event.getPath(), true, new IStatCallback(), "ctx");
            }
        }

    }

    /**
     * 节点存在
     */
    private void eventProcess() {

        zooKeeper.exists("/node3", true, new IStatCallback(), "ctx");

    }

    static class IStatCallback implements AsyncCallback.StatCallback {

        public void processResult(int i, String s, Object o, Stat stat) {
            System.out.println(i);
            System.out.println(s);
            System.out.println(o);
            System.out.println(stat);
        }
    }

}
