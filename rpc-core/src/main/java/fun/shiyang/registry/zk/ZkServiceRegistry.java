package fun.shiyang.registry.zk;
import fun.shiyang.enumeration.RpcError;
import fun.shiyang.exception.RpcException;
import fun.shiyang.loadbalance.LoadBalance;
import fun.shiyang.loadbalance.RandomLoadBalance;
import fun.shiyang.registry.ServiceRegistry;
import fun.shiyang.registry.zk.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author ay
 * @create 2020-09-02 20:27
 */
@Slf4j
public class ZkServiceRegistry implements ServiceRegistry {



    public ZkServiceRegistry() {
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + serviceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }


}
