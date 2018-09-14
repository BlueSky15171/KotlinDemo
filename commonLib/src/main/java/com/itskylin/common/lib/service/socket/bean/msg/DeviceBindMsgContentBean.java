package com.itskylin.common.lib.service.socket.bean.msg;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.Service.socket.bean.msg
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/25 14:40
 */
@SuppressWarnings("all")
public class DeviceBindMsgContentBean extends MsgContentBean {


    /**
     * AreaID : 205
     * SeatBedID : 27687
     * SeatBedNo : 01762457
     * DeviceMac : 9FFEFF570B086E9a
     * SuffererID : 13258
     * TotalDropCount : null
     * DropCount : null
     * isinsert : 0
     * SuffererName : 汪荷英
     * SuffererNo : null
     */

    @JSONField(name = "AreaID")
    public int areaID;
    @JSONField(name = "SeatBedID")
    public String seatBedID;
    @JSONField(name = "SeatBedNo")
    public String seatBedNo;
    @JSONField(name = "DeviceMac")
    public String deviceMac;
    @JSONField(name = "SuffererID")
    public String suffererID;
    @JSONField(name = "TotalDropCount")
    public Integer totalDropCount;
    @JSONField(name = "DropCount")
    public Integer sropCount;
    @JSONField(name = "isinsert")
    public Integer isInsert;
    @JSONField(name = "SuffererName")
    public String suffererName;
    @JSONField(name = "SuffererNo")
    public String suffererNo;

    @Override
    public String toString() {
        return "DeviceBindMsgContentBean{" +
                "areaID='" + areaID + '\'' +
                ", seatBedID='" + seatBedID + '\'' +
                ", seatBedNo='" + seatBedNo + '\'' +
                ", deviceMac='" + deviceMac + '\'' +
                ", suffererID='" + suffererID + '\'' +
                ", totalDropCount=" + totalDropCount +
                ", sropCount=" + sropCount +
                ", isInsert=" + isInsert +
                ", suffererName='" + suffererName + '\'' +
                ", suffererNo='" + suffererNo + '\'' +
                '}';
    }
}
