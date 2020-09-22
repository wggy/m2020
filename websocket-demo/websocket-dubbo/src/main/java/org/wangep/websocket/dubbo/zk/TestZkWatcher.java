package org.wangep.websocket.dubbo.zk;

import org.apache.zookeeper.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 * created by @author: wangep on 2020/5/12 15:18
 */

public class TestZkWatcher {

    static ZooKeeper zk = null;


    private static boolean isNodeExist(String nodePath) {
        try {
            return zk.exists(nodePath, true) != null;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static void createNode() throws KeeperException, InterruptedException {
        zk.create("/tmp_root_path", "It is root path of tmp_root_path".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/tmp_root_path/childPath1", "It is the secondary node of tmp_root_path".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/tmp_root_path/childPath2", "It is the secondary node of tmp_root_path".getBytes(),  ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    private static void createNodeIfNotExist() throws KeeperException, InterruptedException {
        String tmpRootPath = "/tmp_root_path";
        if (isNodeExist(tmpRootPath)) {
            System.out.println(String.format("node[%s] existed", tmpRootPath));
        } else {
            zk.create(tmpRootPath, "It is root path of tmp_root_path".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        String secondChildPath1 = "/tmp_root_path/childPath1";
        if (isNodeExist(secondChildPath1)) {
            System.out.println(String.format("node[%s] existed", secondChildPath1));
        } else {
            zk.create(secondChildPath1, "It is the secondary node of tmp_root_path".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        String secondChildPath2 = "/tmp_root_path/childPath2";
        if (isNodeExist(secondChildPath2)) {
            System.out.println(String.format("node[%s] existed", secondChildPath2));
        } else {
            zk.create(secondChildPath2, "It is the secondary node of tmp_root_path".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }


    public static void main(String[] args) {

        try {
            System.out.println("Starting to connect Zk......");
            String hostPort = "localhost:2181";
            int sessionTimeout = 5000;

            zk = new ZooKeeper(hostPort, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == null) {
                        return;
                    }
                    System.out.println(String.format("触发事件： [%s]， Path： [%s]", event.getType(), event.getPath()));
                }
            });

            System.out.println("Zookeeper connected");

            TimeUnit.SECONDS.sleep(1);

            createNodeIfNotExist();


            System.out.println(new String(zk.getData("/tmp_root_path/childPath2", true, null)));
            isNodeExist("/tmp_root_path/childPath2");
            zk.setData("/tmp_root_path/childPath2", "The second time updating: Child2".getBytes(), -1);
            isNodeExist("/tmp_root_path/childPath2");
            zk.setData("/tmp_root_path/childPath2", "11111111111111".getBytes(), -1);

//            isNodeExist("/tmp_root_path/childPath1");
//            System.out.println(new String(zk.getData("/tmp_root_path/childPath1", true, null)));
//            zk.delete("/tmp_root_path/childPath1", -1);
//            zk.delete("/tmp_root_path/childPath2", -1);
//
//            List<String> childrenList = zk.getChildren("/tmp_root_path", true);
//            if (childrenList == null || childrenList.size() <= 0) {
//                zk.delete("/tmp_root_path", -1);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
