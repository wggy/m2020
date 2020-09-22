package org.wangep.websocket.dubbo.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/***
 * created by @author: wangep on 2020/5/18 16:27
 */
public class DeleteDubboZkNodeService {
    private static ZooKeeper zk = null;

    public static void main(String[] args) {
        System.out.println("Starting to connect Zk......");
        String hostPort = "localhost:2181";
        int sessionTimeout = 5000;

        try {
            zk = new ZooKeeper(hostPort, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == null) {
                        return;
                    }
                    System.out.println(String.format("触发事件： [%s]， Path： [%s]", event.getType(), event.getPath()));
                }
            });

            loopNodes("/dubbo");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loopNodes(String node) throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren(node, true);
        if (list == null || list.size() <= 0) {
            zk.delete(node, -1);
        } else {
            for (String item: list) {
                loopNodes(node + "/" + item);
            }
            loopNodes(node);
        }
    }
}
