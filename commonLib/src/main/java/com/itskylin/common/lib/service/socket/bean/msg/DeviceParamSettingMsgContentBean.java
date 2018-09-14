package com.itskylin.common.lib.service.socket.bean.msg;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.Service.socket.bean.msg
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/25 14:43
 */
@SuppressWarnings("all")
public class DeviceParamSettingMsgContentBean extends MsgContentBean {

    /**
     * DrugGroupID : 16329
     * SpeedMax : 157
     * SpeedMin : 67
     * DropCount : 500
     * PipeType : 1
     * PipeName : 输液管1号
     */

    @JSONField(name = "DrugGroupID")
    public int drugGroupID;
    @JSONField(name = "SpeedMax")
    public int speedMax;
    @JSONField(name = "SpeedMin")
    public int speedMin;
    @JSONField(name = "DropCount")
    public int dropCount;
    @JSONField(name = "PipeType")
    public int pipeType;
    @JSONField(name = "PipeName")
    public String pipeName;

    @Override
    public String toString() {
        return "DeviceParamSettingMsgContentBean{" +
                "drugGroupID=" + drugGroupID +
                ", speedMax=" + speedMax +
                ", speedMin=" + speedMin +
                ", dropCount=" + dropCount +
                ", pipeType=" + pipeType +
                ", pipeName='" + pipeName + '\'' +
                '}';
    }
}
