package com.netty.client;

import com.netty.client.decoder.TimeDecoder;
import com.netty.client.handler.TimeClientHandler;
import com.netty.client.handler.TimeClientHandlerTest;
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

/**
 * @ClassName TimeClientTest
 * @Description TODO  加上解码功能的TimeClient
 * @Author 刘子华
 * @Date 2019/7/12 19:30
 */
public class TimeClientTest {

    @Getter@Setter
    private int port;

    public TimeClientTest(int port) {
        this.port = port;
    }

    public TimeClientTest() {
        this.port = 8888;
    }


    public void run() throws Exception{

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap boot = new Bootstrap();
            boot.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler()).addLast(new TimeDecoder());
                        }
                    });
            //或者
            /*boot.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandlerTest());
                        }
                    });*/

            //启动客户端
            ChannelFuture future = boot.connect("127.0.0.1", getPort()).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        new TimeClientTest().run();
    }

}
