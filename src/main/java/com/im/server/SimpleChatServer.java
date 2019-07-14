package com.im.server;

import com.im.server.handler.SimpleChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName SimpleChatServer
 * @Description TODO
 * @Author 刘子华
 * @Date 2019/7/14 16:25
 */
public class SimpleChatServer {

    @Getter@Setter
    private int port;

    public SimpleChatServer() {
        setPort(8888);
    }

    public SimpleChatServer(int port) {
        this.port = port;
    }



    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new SimpleChatServerInitializer());

            System.out.println("服务端启动！");

            ChannelFuture future = server.bind(getPort()).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("服务端关闭！");
        }
    }

    public static void main(String[] args) throws Exception {

        new SimpleChatServer().run();
    }


}
