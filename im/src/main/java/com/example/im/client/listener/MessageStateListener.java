package com.example.im.client.listener;

/**
 * @author zzxy
 */
public interface MessageStateListener {
    /**
     *发送状态监听
     * @param isSuccess
     */
    void isSendSuccss(boolean isSuccess);
}
