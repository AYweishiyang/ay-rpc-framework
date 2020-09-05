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
     * 服务名与提供服务的对象的对应关系，key为自定义的类名，value为对象
     */
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    /**
     * 当前有哪些对象已经被注册(存储对象名)
     */
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void addServiceProvider(T service,String serviceName) {

        if(registeredService.contains(serviceName)) {
            return;
        }
        registeredService.add(serviceName);
        serviceMap.put(serviceName, service);
        log.info("向接口: {} 注册服务: {}", service.getClass().getInterfaces(), serviceName);
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
