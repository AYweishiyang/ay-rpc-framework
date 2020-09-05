package fun.shiyang.config;

import fun.shiyang.registry.zk.util.CuratorUtils;
import fun.shiyang.utils.threadpool.ThreadPoolFactoryUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ay
 * @create 2020-09-03 9:33
 */
@Slf4j
public class CustomShutdownHook {
    private static final CustomShutdownHook CUSTOM_SHUTDOWN_HOOK = new CustomShutdownHook();

    public static CustomShutdownHook getCustomShutdownHook(){
        return CUSTOM_SHUTDOWN_HOOK;
    }

    public void clearAll(){
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            CuratorUtils.clearRegistry(CuratorUtils.getZkClient());
            ThreadPoolFactoryUtils.shutDownAllThreadPool();
            log.info("ShutdownHook :clearRegistry done");
        }));
    }
}
