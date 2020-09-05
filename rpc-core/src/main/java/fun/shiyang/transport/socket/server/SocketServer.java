package fun.shiyang.transport.socket.server;


import fun.shiyang.handler.RequestHandler;
import fun.shiyang.provider.ServiceProviderImpl;
import fun.shiyang.registry.zk.ZkServiceRegistry;
import fun.shiyang.serializer.CommonSerializer;
import fun.shiyang.transport.AbstractRpcServer;
import fun.shiyang.utils.threadpool.ThreadPoolFactoryUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ay
 * @create 2020-09-01 16:13
 */
@Slf4j
public class SocketServer extends AbstractRpcServer {


    private final ExecutorService threadPool;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactoryUtils.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        ThreadPoolFactoryUtils.printThreadPoolStatus((ThreadPoolExecutor)threadPool);
        this.serviceRegistry = new ZkServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("服务器启动……");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                log.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("服务器启动时有错误发生:", e);
        }
    }

}
