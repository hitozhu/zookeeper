package com.htzhu.client;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by htzhu on 2017/8/26.
 */
public class GetChildrenSync implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * ip:port timeout watch
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildrenSync());
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
                    try {
                        System.err.println(zooKeeper.getChildren(event.getPath(), true));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    /**
     * 创建节点
     */
    private void eventProcess() {

        try {
            List<String> nodes = zooKeeper.getChildren("/", true);
            System.err.println(nodes.toString());

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
