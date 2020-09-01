package fun.shiyang;

/**
 * @author ay
 * @create 2020-09-01 16:29
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(123456789, " a message from client");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
