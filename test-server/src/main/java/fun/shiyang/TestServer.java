package fun.shiyang;

import fun.shiyang.registry.DefaultServiceRegistry;
import fun.shiyang.registry.ServiceRegistry;

/**
 * @author ay
 * @create 2020-09-01 16:27
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
