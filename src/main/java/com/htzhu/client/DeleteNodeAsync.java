package com.htzhu.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by htzhu on 2017/8/26.
 */
public class DeleteNodeAsync implements Watcher {

    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * ip:port timeout watch
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new DeleteNodeAsync());
        System.err.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {

        System.err.println(event);

        // 已链接
        if (event.getState() == Event.KeeperState.SyncConnected) {

            if (event.getType() == Event.EventType.None && null == event.getPath()) {
                eventProcess();
            }
        }

    }

    /**
     * 删除节点
     */
    private void eventProcess() {

        zooKeeper.delete("/node3", -1, new IVoidCallback(), "ctx");
    }

    static class IVoidCallback implements AsyncCallback.VoidCallback {

        public void processResult(int i, String s, Object o) {
            System.err.println(i);
            System.err.println(s);
            System.err.println(o);
        }
    }

}
