package com.itskylin.common.lib.service.socket.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.itskylin.common.lib.service.socket.bean.msg.DeviceStartingupMsgContentBean;

/**
 * 501 设备绑定/解绑（新）
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 17:15
 */
@SuppressWarnings("all")
public class DeviceBindAndUnbindBean extends BaseSocketBean {
    /**
     * Sid : 191
     * DeviceMac :
     * SeatBedDeviceID : 192
     * Mac : C3EB9FFEFF570B00
     * SeatBedID : 27707
     * SerialNo : 00305732
     * SuffererNo : 00305732
     * SuffererName : 马穗林
     * DevVoice : 1
     * DevCode :
     * AreaID : 205
     * AreaCode : 90001
     */

    @JSONField(name = "Sid")
    public int sid;
    @JSONField(name = "DeviceMac")
    public String deviceMac;
    @JSONField(name = "SeatBedDeviceID")
    public int seatBedDeviceID;
    @JSONField(name = "Mac")
    public String mac;
    @JSONField(name = "SeatBedID")
    public String seatBedID;
    @JSONField(name = "SerialNo")
    public String serialNo;
    @JSONField(name = "SuffererNo")
    public String suffererNo;
    @JSONField(name = "SuffererName")
    public String suffererName;
    @JSONField(name = "DevVoice")
    public String devVoice;
    @JSONField(name = "DevCode")
    public String devCode;
    @JSONField(name = "AreaID")
    public String areaID;
    @JSONField(name = "AreaCode")
    public String areaCode;

    public DeviceStartingupMsgContentBean msgContent() {
        String msgContent = formatMsgContent();
        if (!TextUtils.isEmpty(msgContent)) {
            return JSON.parseObject(msgContent, DeviceStartingupMsgContentBean.class);
        }
        return null;
    }

    @Override
    public String toString() {
        return "DeviceBindAndUnbindBean{" +
                "sid=" + sid +
                ", deviceMac='" + deviceMac + '\'' +
                ", seatBedDeviceID=" + seatBedDeviceID +
                ", mac='" + mac + '\'' +
                ", seatBedID='" + seatBedID + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", suffererNo='" + suffererNo + '\'' +
                ", suffererName='" + suffererName + '\'' +
                ", devVoice='" + devVoice + '\'' +
                ", devCode='" + devCode + '\'' +
                ", areaID='" + areaID + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
