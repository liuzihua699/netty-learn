package com.netty.server;

import com.netty.server.handler.DiscardServerHandler;
import com.netty.server.handler.DiscardServerHandlerTest;
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
 * @ClassName DiscardServer
 * @Description TODO    Diescard服务器更正版
 * @Author 刘子华
 * @Date 2019/7/9 22:34
 */
public class DiscardServerTest {

    @Getter@Setter
    private int port;

    public DiscardServerTest(int port) {
        this.port = port;
    }

    public DiscardServerTest() {
        this.port = 8888;
    }


    public void run() throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandlerTest());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //绑定端口
            ChannelFuture channelFuture = boot.bind(port).sync();

            //等待服务器socket关闭
            channelFuture.channel().closeFuture().sync();

        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {

        new DiscardServerTest().run();
        //访问 http://localhost:8888 然后回到控制台能看到访问信息
    }
}
