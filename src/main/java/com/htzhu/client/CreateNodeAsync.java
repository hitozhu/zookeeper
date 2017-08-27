package com.htzhu.client;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by htzhu on 2017/8/26.
 */
public class CreateNodeAsync implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         * ip:port timeout watch
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeAsync());
        System.err.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {

        // 已链接
        if (event.getState() == Event.KeeperState.SyncConnected) {
            eventProcess();
        }

    }

    /**
     * 创建节点
     */
    private void eventProcess() {

        /**
         * 节点路径 值 权限 创建模式（持久、临时）
         */
        zooKeeper.create("/node2", "123456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallBack(), "ctx");
    }

    static class IStringCallBack implements AsyncCallback.StringCallback {

        public void processResult(int i, String s, Object o, String s1) {
            System.err.println(i);
            System.err.println(s);
            System.err.println(o);
            System.err.println(s1);
        }
    }

}
