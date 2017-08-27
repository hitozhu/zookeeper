package com.htzhu.client;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by htzhu on 2017/8/26.
 */
public class NodeExistsSync implements Watcher {

    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * ip:port timeout watch
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new NodeExistsSync());
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
                try {
                    if (event.getType() == Event.EventType.NodeCreated) {
                        System.err.println(event.getPath() + " create");
                    } else if (event.getType() == Event.EventType.NodeDataChanged) {
                        System.err.println(event.getPath() + " data change");
                    } else if (event.getType() == Event.EventType.NodeDeleted) {
                        System.err.println(event.getPath() + " node delete");
                    }

                    System.err.println(zooKeeper.exists(event.getPath(), true));

                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 节点存在
     */
    private void eventProcess() {

        try {
            stat = zooKeeper.exists("/node3", true);
            System.err.println(stat);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
