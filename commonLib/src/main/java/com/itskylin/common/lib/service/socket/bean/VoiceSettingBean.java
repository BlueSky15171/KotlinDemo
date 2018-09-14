package com.itskylin.common.lib.service.socket.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 404 声音设置
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:31
 */
public class VoiceSettingBean extends BaseSocketBean {

    /**
     * MacCode : 2
     * MsgContent : 0
     * MacNo : null
     * SeatBedNo :
     * AreaID : 0
     * AreaCode :
     * PipeType :
     * PipeTypeName :
     */

    @JSONField(name = "MacNo")
    public String macNo;
    @JSONField(name = "SeatBedNo")
    public String seatBedNo;
    @JSONField(name = "AreaID")
    public int areaID;
    @JSONField(name = "AreaCode")
    public String areaCode;
    @JSONField(name = "PipeType")
    public String pipeType;
    @JSONField(name = "PipeTypeName")
    public String pipeTypeName;

    @Override
    public String toString() {
        return "VoiceSettingBean{" +
                "macNo='" + macNo + '\'' +
                ", seatBedNo='" + seatBedNo + '\'' +
                ", areaID=" + areaID +
                ", areaCode='" + areaCode + '\'' +
                ", pipeType='" + pipeType + '\'' +
                ", pipeTypeName='" + pipeTypeName + '\'' +
                ", station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
