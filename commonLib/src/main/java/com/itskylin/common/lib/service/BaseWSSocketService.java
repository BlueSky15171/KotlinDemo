package com.itskylin.common.lib.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.itskylin.common.lib.service.socket.SocketResultFactory;
import com.itskylin.common.lib.service.socket.bean.BaseSocketBean;
import com.itskylin.common.lib.utils.StringUtils;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ByteBufferBackedChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketVersion;
import org.jboss.netty.util.internal.ExecutorUtil;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseWSSocketService extends Service {
    private static final String TAG = "SocketService";
    //    private static final long TASK_DELAY_TIME = 5000;
//    private static final long TASK_DELAY_PERIOD = 20000;
    private static final long TASK_DELAY_TIME = 200;
    private static final long TASK_DELAY_PERIOD = 1000;
    private Channel channel;
    private ExecutorService bossExecutor;
    private ExecutorService workerExecutor;
    protected URI wsUri;
    private Timer timer;
    private TimerTask delaySendHeardbest;

    private void initHeardbestData() {
        timer = new Timer();
        delaySendHeardbest = new TimerTask() {
            @Override
            public void run() {
                if (null != channel && channel.isConnected()) {
                    String str = "@heart";
                    ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes(Charset.forName("UTF-8")));
                    channel.write(new ByteBufferBackedChannelBuffer(byteBuffer));
                    Log.i(TAG, "run: group.write(\"@heart\");");
                    Log.i(TAG, "run: channel.write(\"{\"test\":\"hello\"}\");");
                }
            }
        };
        timer.schedule(delaySendHeardbest, TASK_DELAY_TIME, TASK_DELAY_PERIOD);
    }

    private SimpleChannelUpstreamHandler webSocketHandler = new SimpleChannelUpstreamHandler() {
        @Override
        public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) {

            if (e.getMessage() instanceof TextWebSocketFrame) {
                String json = ((TextWebSocketFrame) e.getMessage()).getText();
                if (!StringUtils.isEmpty(json)) {
                    receiver(SocketResultFactory.createMessate(json));
                }
            } else {
                Log.wtf(TAG,
                        "Unexpected message type received: " + e.getMessage().getClass()
                );
            }
        }
    };

    /**
     * 接收消息
     * @param json
     */
    protected abstract void receiver(BaseSocketBean json);

    @SuppressLint("HandlerLeak")
    class IncomingHandler extends Handler {

        @Override
        public void handleMessage(final Message msg) {
        }
    }

    final Messenger messenger = new Messenger(new IncomingHandler());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(final Intent intent) {
        bossExecutor = Executors.newCachedThreadPool();
        workerExecutor = Executors.newCachedThreadPool();
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        bossExecutor, workerExecutor
                )
        );
        //init websocket server address
        wsUri = initURI();

        final WebSocketClientHandshaker handshaker = new WebSocketClientHandshakerFactory().newHandshaker(
                wsUri, WebSocketVersion.V13, null, false, null
        );

        bootstrap.setPipelineFactory(new WebSocketPipelineFactory(webSocketHandler, wsUri, handshaker));


        final InetSocketAddress remoteAddress = new InetSocketAddress(wsUri.getHost(), wsUri.getPort());
        Log.i(TAG, "Trying to connect to " + remoteAddress);

        ChannelFuture future = bootstrap.connect(remoteAddress);
        future.addListener(new ChannelFutureListener() {
                               public void operationComplete(final ChannelFuture future) {
                                   if (future.isSuccess()) {
                                       channel = future.getChannel();

                                       initHeardbestData();

                                       Log.i(TAG, "Socket connection to " + remoteAddress + " established!");
                                       Log.i(TAG, "Starting handshake...");
                                       handshaker.handshake(channel).addListener(new ChannelFutureListener() {
                                                                                     public void operationComplete(final ChannelFuture future) {
                                                                                         if (future.isSuccess()) {
                                                                                             Log.i(TAG, "Websocket handshake complete!");
                                                                                         } else {
                                                                                             Log.e(TAG,
                                                                                                     "Exception during websocket handshake: " + future.getCause(),
                                                                                                     future.getCause()
                                                                                             );
                                                                                         }
                                                                                     }
                                                                                 }
                                       );
                                   } else {
                                       Log.e(TAG,
                                               "Failed to connect to websocket endpoint " + remoteAddress + ". Reason: " + future
                                                       .getCause(), future.getCause()
                                       );
                                   }
                               }
                           }
        );

        return messenger.getBinder();
    }

    protected abstract URI initURI();

    @Override
    public boolean onUnbind(final Intent intent) {
        try {
            channel.close().await();
        } catch (InterruptedException e) {
            Log.e(TAG, "Exception while closing WebSocket connection: " + e, e);
        }
        ExecutorUtil.terminate(bossExecutor, workerExecutor);
        return super.onUnbind(intent);
    }
}
