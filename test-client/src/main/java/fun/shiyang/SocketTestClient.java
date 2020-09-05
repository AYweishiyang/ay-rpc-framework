package fun.shiyang;

import fun.shiyang.transport.RpcClientProxy;
import fun.shiyang.transport.socket.client.SocketClient;

/**
 * @author ay
 * @create 2020-09-01 16:29
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient();

        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloService2 helloService2 = proxy.getProxy(HelloService2.class);
        HelloObject object = new HelloObject(123456789, " a message from client");
        System.out.println(helloService.hello(object));
        System.out.println(helloService2.hello2(object));

    }
}
