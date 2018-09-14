package com.itskylin.common.lib.service.socket.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

/**
 * 403 心跳
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:27
 */
@SuppressWarnings("all")
public class HeartbeatBean extends BaseSocketBean {

    public HeartbeatBean msgContent() {
        String msgContent = formatMsgContent();
        if (!TextUtils.isEmpty(msgContent)) {
            return JSON.parseObject(msgContent, HeartbeatBean.class);
        }
        return null;
    }

    @Override
    public String toString() {
        return "HeartbeatBean{" +
                "station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
