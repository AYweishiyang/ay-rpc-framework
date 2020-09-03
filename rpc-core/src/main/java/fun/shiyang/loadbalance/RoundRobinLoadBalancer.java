package fun.shiyang.loadbalance;

import java.util.List;

/**
 * @author ay
 * @create 2020-09-03 9:16
 */
public class RoundRobinLoadBalancer implements LoadBalance{
    private int index = 0;
    @Override
    public String selectServiceAddress(List<String> serviceAddresses) {
        if(index >= serviceAddresses.size()){
            index %= serviceAddresses.size();
        }
        return serviceAddresses.get(index++);
    }
}
