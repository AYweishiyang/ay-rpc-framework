package fun.shiyang.transport.netty.server;


import fun.shiyang.coder.CommonDecoder;
import fun.shiyang.coder.CommonEncoder;
import fun.shiyang.serializer.KryoSerializer;
import fun.shiyang.transport.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ay
 * @create 2020-09-01 22:07
 */
@Slf4j
public class NettyServer implements RpcServer {

    @Override
    public void start(int port) {
        //2.创建线程池 boss负责请求转发 worker负责相应处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //1.创建服务启动引导
            ServerBootstrap serverBootstrap = new ServerBootstrap();


            serverBootstrap.group(bossGroup, workerGroup)   //3.关联线程池
                    .channel(NioServerSocketChannel.class)  //4.设置Nio服务端实现
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() { //5.初始化通讯管道 -----重点
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new CommonEncoder(new KryoSerializer()));
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            //6.绑定端口启动netty服务
            ChannelFuture future = serverBootstrap.bind(port).sync();
            //7.关闭与客户端间的通讯管道
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("启动服务器时有错误发生: ", e);
        } finally {
            //8.释放线程资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
