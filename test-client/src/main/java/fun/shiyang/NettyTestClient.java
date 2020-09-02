package fun.shiyang;

import fun.shiyang.transport.RpcClient;
import fun.shiyang.transport.RpcClientProxy;
import fun.shiyang.transport.netty.client.NettyClient;

/**
 * @author ay
 * @create 2020-09-01 22:59
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(123456789, "This is a message from client");
        String res = helloService.hello(object);
        System.out.println(res);
    }

}
