package fun.shiyang.provider;

/**
 * @author ay
 * @create 2020-09-01 18:14
 */
public interface ServiceProvider {
    <T> void addServiceProvider(T service);

    Object getServiceProvider(String serviceName);
}
