package fun.shiyang.transport;


import fun.shiyang.entity.RpcRequest;
import fun.shiyang.entity.RpcResponse;
import fun.shiyang.transport.netty.client.NettyClient;
import fun.shiyang.transport.socket.client.SocketClient;
import fun.shiyang.utils.RpcMessageChecker;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author ay
 * @create 2020-09-01 15:58
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {


    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(),
                method.getDeclaringClass().getName(),
                method.getName(),
                args,
                method.getParameterTypes(),
                false);
        RpcResponse rpcResponse = null;
        if (client instanceof NettyClient) {
            try {
                log.info("NettyClient begin sendRequest");
                CompletableFuture<RpcResponse> completableFuture = (CompletableFuture<RpcResponse>) client.sendRequest(rpcRequest);
                //这里会阻塞
                rpcResponse = completableFuture.get();
                log.info("receive rpcResponse = " + rpcResponse);
            } catch (Exception e) {
                log.error("方法调用请求发送失败", e);
                return null;
            }
        }
        if (client instanceof SocketClient) {
            rpcResponse = (RpcResponse) client.sendRequest(rpcRequest);
        }
        RpcMessageChecker.check(rpcRequest, rpcResponse);
        return rpcResponse.getData();
    }
}
