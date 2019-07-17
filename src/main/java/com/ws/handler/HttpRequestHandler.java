package com.ws.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @ClassName HttpRequestHandler
 * @Description TODO
 * @Author 刘子华
 * @Date 2019/7/15 2:41
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    private final String wsUri;
    private static final File INDEX;

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI() + "WebSocketChatClient.html";
            path = !path.contains("file:") ? path : path.substring(5);
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to locate WebSocketChatClient.html", e);
        }
    }

    public HttpRequestHandler(String wsUrl) {
        this.wsUri = wsUrl;
    }



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {



    }



}
