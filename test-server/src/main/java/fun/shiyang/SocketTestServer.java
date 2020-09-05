package fun.shiyang;

import fun.shiyang.annotation.ServiceScan;
import fun.shiyang.serializer.CommonSerializer;
import fun.shiyang.transport.RpcServer;
import fun.shiyang.transport.socket.server.SocketServer;


/**
 * @author ay
 * @create 2020-09-01 16:27
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.KRYO_SERIALIZER);
        server.start();
    }
}
