package com.example.im.client.handler;

import android.text.TextUtils;
import android.util.Log;

import com.example.im.client.listener.NettyClientListener;
import com.example.im.client.status.ConnectState;
import com.example.im.client.uitl.SmartCarProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * @author zzxy
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {
    private static final String TAG = "NettyClientHandler";
    private final boolean isSendheartBeat;
    private NettyClientListener listener;
    private int index;
    private Object heartBeatData;
    private String packetSeparator;

    //    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat"+System.getProperty("line.separator"),
//            CharsetUtil.UTF_8));
    byte[] requestBody = {(byte) 0xFE, (byte) 0xED, (byte) 0xFE, 5, 4, (byte) 0xFF, 0x0a};


    public NettyClientHandler(NettyClientListener listener, int index, boolean isSendheartBeat, Object heartBeatData) {
        this(listener, index, isSendheartBeat, heartBeatData, null);
    }

    public NettyClientHandler(NettyClientListener listener, int index, boolean isSendheartBeat, Object heartBeatData, String separator) {
        this.listener = listener;
        this.index = index;
        this.isSendheartBeat = isSendheartBeat;
        this.heartBeatData = heartBeatData;
        this.packetSeparator = TextUtils.isEmpty(separator) ? System.getProperty("line.separator") : separator;
    }

    /**
     * <p>设定IdleStateHandler心跳检测每x秒进行一次读检测，
     * 如果x秒内ChannelRead()方法未被调用则触发一次userEventTrigger()方法 </p>
     *
     * @param ctx ChannelHandlerContext
     * @param evt IdleStateEvent
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {   //发送心跳
//                ctx.channel().writeAndFlush("Heartbeat" + System.getProperty("line.separator"));
                if (isSendheartBeat) {
                    if (heartBeatData == null) {
                        ByteBuf buf = Unpooled.copiedBuffer(requestBody);
                        ctx.channel().writeAndFlush(buf);
                    } else if (heartBeatData instanceof byte[]) {
                        ByteBuf buf = Unpooled.copiedBuffer((byte[]) heartBeatData);
                        ctx.channel().writeAndFlush(buf);
                    } else {
                        Log.e(TAG, "userEventTriggered: heartBeatData type error");
                    }
                } else {
                    Log.e(TAG, "不发送心跳");
                }
            }
        }
    }

    /**
     * <p>客户端上线</p>
     *
     * @param ctx ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Log.e(TAG, "channelActive");
//        NettyTcpClient.getInstance().setConnectStatus(true);
        listener.onClientStatusConnectChanged(ConnectState.STATUS_CONNECT_SUCCESS, index);
    }

    /**
     * <p>客户端下线</p>
     *
     * @param ctx ChannelHandlerContext
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Log.e(TAG, "channelInactive");
//        NettyTcpClient.getInstance().setConnectStatus(false);
//        listener.onServiceStatusConnectChanged(NettyClientListener.STATUS_CONNECT_CLOSED);
        // NettyTcpClient.getInstance().reconnect();
    }

    /**
     * 客户端收到消息
     *
     * @param channelHandlerContext ChannelHandlerContext
     * @param msg                   消息
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg)  throws Exception{
        try {
//            ByteBuf buf = msg;
//            byte[] bytes = new byte[buf.readableBytes()];
//            buf.readBytes(bytes);
//            String result = new String(bytes, "utf-8");
//            Log.e(TAG, "Server: " + result);
            SmartCarProtocol body = (SmartCarProtocol) msg;
            String content=new String(body.getContent());
//            LogUtil.i(TAG,"Server: " + content);
            listener.onMessageResponseClient(content, index);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    /**
     * @param ctx   ChannelHandlerContext
     * @param cause 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
//        NettyTcpClient.getInstance().setConnectStatus(false);
        Log.e(TAG, "exceptionCaught");
        listener.onClientStatusConnectChanged(ConnectState.STATUS_CONNECT_ERROR, index);
        cause.printStackTrace();
        ctx.close();
    }

}
