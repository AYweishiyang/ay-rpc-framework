package fun.shiyang;

import fun.shiyang.registry.DefaultServiceRegistry;
import fun.shiyang.registry.ServiceRegistry;
import fun.shiyang.transport.netty.server.NettyServer;

/**
 * @author ay
 * @create 2020-09-01 22:58
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }

}
