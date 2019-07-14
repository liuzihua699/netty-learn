package com.im.client;

import com.im.client.handler.SimpleChatClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @ClassName SimpleChatClinet
 * @Description TODO
 * @Author 刘子华
 * @Date 2019/7/14 16:42
 */
public class SimpleChatClient {

    @Getter@Setter
    private int port;

    @Getter@Setter
    private String host;

    public SimpleChatClient() {
        setPort(8888);
        setHost("127.0.0.1");
    }

    public SimpleChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run () throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            Bootstrap client = new Bootstrap();
            client.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            Channel channel = client.connect(getHost(), getPort()).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new SimpleChatClient().run();
    }

}
