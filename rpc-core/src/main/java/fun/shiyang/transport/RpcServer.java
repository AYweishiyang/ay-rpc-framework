package fun.shiyang.transport;

import fun.shiyang.serializer.CommonSerializer;

/**
 * @author ay
 * @create 2020-09-01 21:53
 */
public interface RpcServer {
    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    <T> void publishService(T service, String serviceName);
}
