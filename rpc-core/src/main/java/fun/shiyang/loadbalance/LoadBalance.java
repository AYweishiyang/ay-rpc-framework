package fun.shiyang.loadbalance;

import java.util.List;

/**
 * @author ay
 * @create 2020-09-02 20:53
 */
public interface LoadBalance {
    /**
     * Choose one from the list of existing service addresses list
     *
     * @param serviceAddresses Service address list
     * @return target service address
     */
    String selectServiceAddress(List<String> serviceAddresses);
}
