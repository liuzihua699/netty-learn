package com.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;

/**
 * @ClassName DiscardServerHandler
 * @Description TODO Discard协议服务器处理器
 *  丢弃服务器(DiscardServer)，即拒绝任何受到的数据，不响应。
 * @Author 刘子华
 * @Date 2019/7/8 23:29
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ((ByteBuf) msg).release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
