package com.netty.client;

import com.netty.client.handler.TimeClientHandler;
import com.netty.server.DiscardServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.SocketHandler;

/**
 * @ClassName TimeClient
 * @Description TODO   Client连接服务端，并且获取服务端的时间，然后打印在客户端的控制台上。
 * @Author 刘子华
 * @Date 2019/7/12 14:25
 */
public class TimeClient {

    @Getter@Setter
    private int port;

    public TimeClient(int port) {
        this.port = port;
    }

    public TimeClient() {
        this.port = 8888;
    }


    public void run() throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap boot = new Bootstrap();
            boot.group(workerGroup)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });

            //启动客户端
            ChannelFuture future = boot.connect("127.0.0.1", getPort()).sync();
            //等待连接关闭
            future.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        new TimeClient().run();
        //先运行TimeServer.run()然后 运行TimeClient.run() 即可看到由服务端发送过来的消息。
    }
}
