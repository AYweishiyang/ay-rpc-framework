package fun.shiyang;

import fun.shiyang.transport.RpcClientProxy;
import fun.shiyang.transport.socket.client.SocketClient;

/**
 * @author ay
 * @create 2020-09-01 16:29
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(123456789, " a message from client");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
