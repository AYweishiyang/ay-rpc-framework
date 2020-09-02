package fun.shiyang;

import fun.shiyang.registry.DefaultServiceRegistry;
import fun.shiyang.registry.ServiceRegistry;
import fun.shiyang.transport.socket.server.SocketServer;


/**
 * @author ay
 * @create 2020-09-01 16:27
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer rpcServer = new SocketServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
