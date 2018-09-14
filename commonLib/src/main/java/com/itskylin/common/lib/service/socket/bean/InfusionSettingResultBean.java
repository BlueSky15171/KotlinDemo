package com.itskylin.common.lib.service.socket.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 421 输液管设置结果
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 17:12
 */
@SuppressWarnings("all")
public class InfusionSettingResultBean extends BaseSocketBean {

    /**
     * MacCode : 2
     * MsgContent : null
     * SeatBedNo : null
     */

    @JSONField(name = "SeatBedNo")
    public String seatBedNo;

    @Override
    public String toString() {
        return "InfusionSettingResultBean{" +
                "seatBedNo='" + seatBedNo + '\'' +
                ", station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
