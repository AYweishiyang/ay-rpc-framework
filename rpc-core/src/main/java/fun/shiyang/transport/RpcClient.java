package fun.shiyang.transport;

import fun.shiyang.entity.RpcRequest;
import fun.shiyang.serializer.CommonSerializer;

/**
 * @author ay
 * @create 2020-09-01 21:56
 */
public interface RpcClient {
    void setSerializer(CommonSerializer serializer);
    Object sendRequest(RpcRequest rpcRequest);
}
