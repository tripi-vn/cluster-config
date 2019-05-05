package vn.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        ZkConnector zkConnector = new ZkConnector();
        ZooKeeper zk = zkConnector.connect("192.168.100.25");
        String newNode = "/deepakDate"+new Date();

    }
}
