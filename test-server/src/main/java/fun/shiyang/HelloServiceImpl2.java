package fun.shiyang;

import fun.shiyang.annotation.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ay
 * @create 2020-09-05 18:04
 */
@Slf4j
@Service
public class HelloServiceImpl2 implements HelloService2{
    @Override
    public String hello2(HelloObject object) {
        log.info("接收到：{}", object.getMessage());
        return "这是调用的返回值，id=" + object.getId() + " message=" + object.getMessage();
    }
}
