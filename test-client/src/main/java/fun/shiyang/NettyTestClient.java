package fun.shiyang;

import fun.shiyang.registry.zk.ZkServiceRegistry;
import fun.shiyang.serializer.KryoSerializer;
import fun.shiyang.transport.RpcClient;
import fun.shiyang.transport.RpcClientProxy;
import fun.shiyang.transport.netty.client.NettyClient;

/**
 * @author ay
 * @create 2020-09-01 22:59
 */
public class NettyTestClient {

    public static void main(String[] args) {

        RpcClient client = new NettyClient(new ZkServiceRegistry());
        client.setSerializer(new KryoSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }

}
