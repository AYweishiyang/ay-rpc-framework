package fun.shiyang;

import fun.shiyang.serializer.KryoSerializer;
import fun.shiyang.transport.netty.server.NettyServer;

/**
 * @author ay
 * @create 2020-09-01 22:58
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new KryoSerializer());
        server.publishService(helloService,HelloService.class);
    }

}
