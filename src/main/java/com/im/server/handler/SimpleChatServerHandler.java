package com.im.server.handler;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SimpleChatServerHandler
 * @Description TODO
 * @Author 刘子华
 * @Date 2019/7/14 14:52
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel cl : channels) {
            cl.writeAndFlush("[SERVER] - " + channel.remoteAddress() + "加入聊天室。\n");
        }
        channels.add(channel);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel cl : channels) {
            cl.writeAndFlush("[SERVER] - " + channel.remoteAddress() + "离开。\n");
        }
        channels.remove(channel);
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel channel = ctx.channel();
        for (Channel cl : channels) {
            if (cl == channel) {
                channel.writeAndFlush("[you]" + s + "\n");
            }else {
                channel.writeAndFlush("[" + channel.remoteAddress() + "]" + s + "\n");
            }
        }
    }



    /**
     *  在channel组中通知信息，可以指定排除的对象
     * @param content   欲通知内容
     * @param excludes   排除某个channel
     */
    private void notic(String content, Channel... excludes) throws Exception {

    }




    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("SimpleChatChannel:" + channel.remoteAddress() + "上线。");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("SimpleChatChannel:" + channel.remoteAddress() + "掉线。");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("SimpleChatChannel:" + channel.remoteAddress() + "异常。");

        cause.printStackTrace();
        ctx.close();
    }
}
