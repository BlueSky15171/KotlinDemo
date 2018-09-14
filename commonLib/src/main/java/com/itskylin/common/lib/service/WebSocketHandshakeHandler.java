package com.itskylin.common.lib.service;

import android.util.Log;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.DefaultChannelFuture;
import org.jboss.netty.channel.DownstreamMessageEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;


/**
 * Handles handshakes and messages
 */
public class WebSocketHandshakeHandler extends SimpleChannelUpstreamHandler {

	private static final String WEBSOCKET_PATH = "/websocket";

	private final WebSocketClientHandshaker handshaker;

	public WebSocketHandshakeHandler(final WebSocketClientHandshaker handshaker) {
		this.handshaker = handshaker;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

		Channel ch = ctx.getChannel();
		if (!handshaker.isHandshakeComplete()) {
			handshaker.finishHandshake(ch, (HttpResponse) e.getMessage());
			Log.i(WebSocketHandshakeHandler.class.getName(), "WebSocket Client connected!");
			return;
		}

		if (!(e.getMessage() instanceof WebSocketFrame)) {
			throw new RuntimeException("Unexpected message received: " + e.getMessage());
		}

		handleWebSocketFrame(ctx, e);
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, MessageEvent e) {

		WebSocketFrame frame = (WebSocketFrame) e.getMessage();

		if (frame instanceof PingWebSocketFrame) {

			ctx.sendDownstream(
					new DownstreamMessageEvent(
							ctx.getChannel(),
							new DefaultChannelFuture(ctx.getChannel(), true),
							new PongWebSocketFrame(frame.getBinaryData()),
							ctx.getChannel().getRemoteAddress()
					)
			);
			return;

		} else if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass()
					.getName()
			)
			);
		}

		ctx.sendUpstream(e);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		Log.e(WebSocketHandshakeHandler.class.getName(), "Upstream exception caught: " + e.getCause(), e.getCause());
	}
}