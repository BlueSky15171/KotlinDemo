package com.itskylin.common.lib.service.socket.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:22
 */
@SuppressWarnings("all")
public class BaseSocketBean implements Serializable {
    /**
     * Station : 5
     * MacCode : 28
     * MessageType : 411
     * MsgContent : null
     * Result : true
     * MacAddr : 86E99FFEFF570B00
     */

    /**
     *
     */
    @JSONField(name = "Station")
    public int station;
    /**
     *
     */
    @JSONField(name = "MacCode")
    public int macCode;
    /**
     *
     */
    @JSONField(name = "MessageType")
    public int messageType;
    /**
     *
     */
    @JSONField(name = "Result")
    public boolean result;
    /**
     *
     */
    @JSONField(name = "MacAddr")
    public String macAddress;

    /**
     * MsgContent : {"speed":0,"workstatus":3,"electricity":75,"totalCount":0,"Maxspeed":200,"Minspeed":6,"Beepstatus":0}
     */
    @JSONField(name = "MsgContent")
    public String msgContents;


    protected String formatMsgContent() {
        if (!TextUtils.isEmpty(msgContents)) {
            return msgContents.replace("\"{", "{").replace("}\"", "}");
        }
        return null;
    }
}
