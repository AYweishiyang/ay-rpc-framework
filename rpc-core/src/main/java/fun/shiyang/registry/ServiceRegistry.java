package fun.shiyang.registry;

import java.net.InetSocketAddress;

/**
 * @author ay
 * @create 2020-09-02 16:07
 */
public interface ServiceRegistry {
    /**
     * 将一个服务注册进注册表
     *
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);



}