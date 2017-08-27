package com.htzhu.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * Created by htzhu on 2017/8/26.
 */
public class GetChildrenAsync implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * ip:port timeout watch
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildrenAsync());
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
                if (event.getType() == Event.EventType.NodeChildrenChanged) {
                    zooKeeper.getChildren(event.getPath(), true, new IChildren2Callback(), null);
                }
            }
        }

    }

    /**
     * 获取子节点
     */
    private void eventProcess() {

        zooKeeper.getChildren("/", true, new IChildren2Callback(), null);

    }

    static class IChildren2Callback implements AsyncCallback.Children2Callback {

        public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
            System.err.println(i);
            System.err.println(s);
            System.err.println(o);
            System.err.println(list.toString());
            System.err.println(stat);
        }
    }

}
