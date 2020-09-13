package fun.shiyang.transport.netty.client;


import fun.shiyang.entity.RpcRequest;
import fun.shiyang.entity.RpcResponse;
import fun.shiyang.enumeration.RpcError;
import fun.shiyang.exception.RpcException;
import fun.shiyang.factory.SingletonFactory;
import fun.shiyang.loadbalance.RandomLoadBalance;
import fun.shiyang.registry.ServiceDiscovery;
import fun.shiyang.registry.zk.ZkServiceDiscovery;
import fun.shiyang.serializer.CommonSerializer;
import fun.shiyang.transport.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * 使用Netty传输数据
 * @author ay
 * @create 2020-09-01 22:21
 */
@Slf4j
public class NettyClient implements RpcClient {


    private static final Bootstrap bootstrap;


    private final ServiceDiscovery serviceDiscovery = new ZkServiceDiscovery(new RandomLoadBalance());
    private static final EventLoopGroup group;
    private CommonSerializer serializer;
    private final UnprocessedRequests unprocessedRequests;

    public NettyClient(Integer serializer) {
        this.serializer = CommonSerializer.getByCode(serializer);
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
    }

    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        CompletableFuture<RpcResponse> resultFuture = new CompletableFuture<>();
        try {
            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if (!channel.isActive()) {
                group.shutdownGracefully();
                return null;
            }
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    log.error("发送消息时有错误发生: ", future.cause());
                }
            });
        } catch (InterruptedException e) {
            unprocessedRequests.remove(rpcRequest.getRequestId());
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        log.info("return resultFuture = " +resultFuture);
        return resultFuture;
    }

//    未使用CompletableFuture
//    @Override
//    public Object sendRequest1(RpcRequest rpcRequest) {
//        if(serializer == null) {
//            log.error("未设置序列化器");
//            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
//        }
//        AtomicReference<Object> result = new AtomicReference<>(null);
//        try {
//            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
//            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
//
//            if(channel.isActive()) {
//                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
//                    if (future1.isSuccess()) {
//                        log.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
//                    } else {
//                        log.error("发送消息时有错误发生: ", future1.cause());
//                    }
//                });
//                //阻塞
//                channel.closeFuture().sync();
//                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
//                RpcResponse rpcResponse = channel.attr(key).get();
//                RpcMessageChecker.check(rpcRequest, rpcResponse);
//                result.set(rpcResponse.getData());
//
//            } else {
//                System.exit(0);
//            }
//        } catch (InterruptedException e) {
//            log.error("发送消息时有错误发生: ", e);
//        }
//        return result.get();
//    }
}
