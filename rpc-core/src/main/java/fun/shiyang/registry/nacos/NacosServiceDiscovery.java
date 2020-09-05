package fun.shiyang.registry.nacos;

import fun.shiyang.loadbalance.LoadBalance;
import fun.shiyang.loadbalance.RandomLoadBalance;
import fun.shiyang.registry.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author ay
 * @create 2020-09-05 10:55
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public NacosServiceDiscovery(LoadBalance loadBalancer) {
        if(loadBalancer == null) {
            this.loadBalance = new RandomLoadBalance();
        } else {
            this.loadBalance = loadBalancer;
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        return null;
    }
}
