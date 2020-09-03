package fun.shiyang.transport;

import fun.shiyang.serializer.CommonSerializer;

/**
 * @author ay
 * @create 2020-09-01 21:53
 */
public interface RpcServer {
    void start();
    void setSerializer(CommonSerializer serializer);
    <T> void publishService(Object service, Class<T> serviceClass);
}
