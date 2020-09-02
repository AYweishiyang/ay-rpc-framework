package fun.shiyang.registry.zk;

import fun.shiyang.enumeration.RpcErrorMessage;
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

    private final LoadBalance loadBalance;

    public ZkServiceRegistry() {
        this.loadBalance = new RandomLoadBalance();
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + serviceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, serviceName);
        if (serviceUrlList.size() == 0) {
            throw new RpcException(RpcErrorMessage.SERVICE_CAN_NOT_BE_FOUND, serviceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
