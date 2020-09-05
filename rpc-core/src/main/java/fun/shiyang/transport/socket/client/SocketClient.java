package fun.shiyang.transport.socket.client;

import fun.shiyang.entity.RpcRequest;
import fun.shiyang.entity.RpcResponse;
import fun.shiyang.enumeration.ResponseCode;
import fun.shiyang.enumeration.RpcError;
import fun.shiyang.exception.RpcException;
import fun.shiyang.loadbalance.LoadBalance;
import fun.shiyang.loadbalance.RandomLoadBalance;
import fun.shiyang.registry.ServiceDiscovery;
import fun.shiyang.registry.nacos.NacosServiceDiscovery;
import fun.shiyang.registry.nacos.NacosServiceRegistry;
import fun.shiyang.registry.ServiceRegistry;
import fun.shiyang.serializer.CommonSerializer;
import fun.shiyang.transport.RpcClient;
import fun.shiyang.transport.socket.util.ObjectReader;
import fun.shiyang.transport.socket.util.ObjectWriter;
import fun.shiyang.utils.RpcMessageChecker;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 使用socket传输
 * @author ay
 * @create 2020-09-01 23:28
 */
@Slf4j
public class SocketClient implements RpcClient {

    private final ServiceDiscovery serviceDiscovery;

    private final CommonSerializer serializer;

    public SocketClient() {
        this(DEFAULT_SERIALIZER, new RandomLoadBalance());
    }
    public SocketClient(LoadBalance loadBalance) {
        this(DEFAULT_SERIALIZER, loadBalance);
    }
    public SocketClient(Integer serializer) {
        this(serializer, new RandomLoadBalance());
    }

    public SocketClient(Integer serializer, LoadBalance loadBalancer) {
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
    }



    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            if (rpcResponse == null) {
                log.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                log.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse.getData();
        } catch (IOException e) {
            log.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

}
