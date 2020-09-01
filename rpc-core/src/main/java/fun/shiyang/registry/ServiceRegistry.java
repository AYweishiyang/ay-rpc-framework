package fun.shiyang.registry;

import java.net.InetSocketAddress;

/**
 * @author ay
 * @create 2020-09-01 18:14
 */
public interface ServiceRegistry {
    <T> void register(T service);
    Object getService(String serviceName);
}
