package fun.shiyang.provider;

import fun.shiyang.enumeration.RpcError;
import fun.shiyang.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ay
 * @create 2020-09-01 18:16
 */
@Slf4j
public class ServiceProviderImpl implements ServiceProvider {
    /**
     * 服务名与提供服务的对象的对应关系，key为接口名，value为实现对应接口的对象，所以一个接口只可以有一个实现
     */
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    /**
     * 当前有哪些对象已经被注册(存储对象名)
     */
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void addServiceProvider(T service) {
        //获取全限定类名
        String serviceName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceName)) {
            return;
        }
        //将serviceName添加到set中
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        log.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
