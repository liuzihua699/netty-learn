package com.netty.client.decoder;

import com.netty.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName TimeDecoder
 * @Description TODO
 * @Author 刘子华
 * @Date 2019/7/12 19:25
 */
public class TimeDecoder2 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        list.add(new UnixTime(byteBuf.readUnsignedInt()));
    }
}
