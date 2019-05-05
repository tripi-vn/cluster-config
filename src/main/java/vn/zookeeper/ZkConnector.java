package vn.zookeeper;

import com.google.inject.Singleton;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

@Singleton
public class ZkConnector {

    private ZooKeeper zk;
    private CountDownLatch connSignal = new CountDownLatch(0);

    public ZooKeeper connect(String host) throws Exception {
        zk = new ZooKeeper(host, 3000, new Watcher() {
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    connSignal.countDown();
                }
            }
        });
        connSignal.await();
        return zk;
    }

    public void createNode(String path, byte[] data) throws Exception {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void updateNode(String path, byte[] data) throws Exception {
        zk.setData(path, data, zk.exists(path, true).getVersion());
    }

    public void deleteNode(String path) throws Exception {
        zk.delete(path,  zk.exists(path, true).getVersion());
    }

    public void close() throws InterruptedException {
        zk.close();
    }
}
