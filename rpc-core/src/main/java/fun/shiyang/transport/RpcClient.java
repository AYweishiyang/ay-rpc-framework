package fun.shiyang.transport;

import fun.shiyang.entity.RpcRequest;
import fun.shiyang.serializer.CommonSerializer;

/**
 * @author ay
 * @create 2020-09-01 21:56
 */
public interface RpcClient {
    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;
    Object sendRequest(RpcRequest rpcRequest);
}
