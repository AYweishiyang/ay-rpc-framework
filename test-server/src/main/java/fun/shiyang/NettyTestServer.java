package fun.shiyang;

import fun.shiyang.annotation.ServiceScan;
import fun.shiyang.serializer.CommonSerializer;
import fun.shiyang.transport.RpcServer;
import fun.shiyang.transport.netty.server.NettyServer;

/**
 * @author ay
 * @create 2020-09-01 22:58
 */
@ServiceScan
public class NettyTestServer {

    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }

}
