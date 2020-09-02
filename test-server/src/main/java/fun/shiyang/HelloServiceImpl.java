package fun.shiyang;


import lombok.extern.slf4j.Slf4j;

/**
 * @author ay
 * @create 2020-09-01 12:49
 */
@Slf4j
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(HelloObject object) {
        log.info("接收到：{}", object.getMessage());
        return "这是调用的返回值，id=" + object.getId() + " message=" + object.getMessage();
    }
}
