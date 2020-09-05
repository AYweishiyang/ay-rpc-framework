package fun.shiyang.registry.zk;

import fun.shiyang.enumeration.RpcError;
import fun.shiyang.exception.RpcException;
import fun.shiyang.loadbalance.LoadBalance;
import fun.shiyang.loadbalance.RandomLoadBalance;
import fun.shiyang.registry.ServiceDiscovery;
import fun.shiyang.registry.zk.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author ay
 * @create 2020-09-05 10:14
 */
@Slf4j
public class ZkServiceDiscovery implements ServiceDiscovery {

    private final LoadBalance loadBalance;

    public ZkServiceDiscovery(LoadBalance loadBalance) {
        if(loadBalance == null) {
            this.loadBalance = new RandomLoadBalance();
        } else {
            this.loadBalance = loadBalance;
        }
    }
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, serviceName);
        if (serviceUrlList.size() == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND, serviceName);
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
