package com.example.im.client.status;

/**
 * @author zzxy
 */
public class ConnectState {
    public final static int STATUS_CONNECT_SUCCESS = 1;

    public final static int STATUS_CONNECT_CLOSED = 0;

    public final static int STATUS_CONNECT_ERROR = -1;
    /** 客户端登陆请求 */
    public static final int loginReq = 30001;
    /** 服务端登陆响应 */
    public static final int loginRes = 30002;
    /** 客户端发送心跳请求***/
    public static final int clientHeartBeatReq = 40001;
    /** 客户端发送心跳响应***/
    public static final int clientHeartBeatRes = 40002;
    /**服务端主动心跳发送***/
    public static final int serverHeartBeatReq = 50001;
    /**服务端主动心跳响应***/
    public static final int serverHeartBeatRes = 50002;
    /**服务端推送消息**/
    public static final int serverPushImReq = 60001;
    /**服务端推送消息客户端响应**/
    public static final int serverPushImRes = 60002;
    /**
     * 1.文本消息
     */
    public final static String NEWSTYPE_TEXT = "text";
    /**
     * 2.图片消息
     */
    public final static String NEWSTYPE_IMG = "img";
    /**
     * 3.语音消息
     */
    public final static String NEWSTYPE_VOICE = "voice";
    /**
     * 4.视频消息
     */
    public final static String NEWSTYPE_VIDEO = "video";
    /**
     * 5.文件消息
     */
    public final static String NEWSTYPE_FILE = "file";
    /**
     * 红包消息
     */
    public final static String NEWSTYPE_RED_PACKET = "redpacket";
    /**
     * 自定义消息
     */
    public final static String NEWSTYPE_CUSTOM = "custom";
    /**
     * 关注消息
     */
    public final static String NEWSTYPE_FOLLOW = "follow";
    /**
     * 礼物消息
     */
    public final static String NEWSTYPE_GIFT = "gift";
    /**
     * 视频通话
     */
    public final static String NEWSTYPE_CALL_VIDEO = "callVideo";

    /**
     * 语音通话
     */
    public final static String NEWSTYPE_CALL_VOICE = "callVoice";

    /**
     * 全服推送消息
     */
    public final static String NEWSTYPE_FULL_PUSH = "fullPush";

}
