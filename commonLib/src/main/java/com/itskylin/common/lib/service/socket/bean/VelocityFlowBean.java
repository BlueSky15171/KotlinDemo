package com.itskylin.common.lib.service.socket.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 410 滴速上下限设置
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:38
 */
@SuppressWarnings("all")
public class VelocityFlowBean extends BaseSocketBean {

    /**
     * SpeedMin : 56
     * SpeedMax : 146
     * SuffererID : 2834
     * DrugGroupID : 16329
     */

    @JSONField(name = "SpeedMin")
    public int speedMin;
    @JSONField(name = "SpeedMax")
    public int speedMax;
    @JSONField(name = "SuffererID")
    public int suffererID;
    @JSONField(name = "DrugGroupID")
    public int drugGroupID;

    @Override
    public String toString() {
        return "VelocityFlowBean{" +
                "speedMin=" + speedMin +
                ", speedMax=" + speedMax +
                ", suffererID=" + suffererID +
                ", drugGroupID=" + drugGroupID +
                ", station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
