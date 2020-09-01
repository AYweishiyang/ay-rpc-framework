package fun.shiyang;

import fun.shiyang.registry.DefaultServiceRegistry;
import fun.shiyang.registry.ServiceRegistry;


/**
 * @author ay
 * @create 2020-09-01 16:27
 */
public class SocketServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        fun.shiyang.transport.socket.server.SocketServer rpcServer = new fun.shiyang.transport.socket.server.SocketServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
