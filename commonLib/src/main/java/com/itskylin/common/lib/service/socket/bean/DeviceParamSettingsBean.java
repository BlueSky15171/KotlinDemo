package com.itskylin.common.lib.service.socket.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.itskylin.common.lib.service.socket.bean.msg.DeviceParamSettingMsgContentBean;

/**
 * 499 设备参数设置
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 17:17
 */
@SuppressWarnings("all")
public class DeviceParamSettingsBean extends BaseSocketBean {

    /**
     * MacCode : 2
     * MsgContent : {"DrugGroupID":16329,"SpeedMax":157,"SpeedMin":67,"DropCount":500,"PipeType":1,"PipeName":"输液管1号"}
     * SeatBedNo : null
     */

    @JSONField(name = "SeatBedNo")
    public String seatBedNo;

    public DeviceParamSettingMsgContentBean msgContent() {
        String msgContent = formatMsgContent();
        if (!TextUtils.isEmpty(msgContent)) {
            return JSON.parseObject(msgContent, DeviceParamSettingMsgContentBean.class);
        }
        return null;
    }

    @Override
    public String toString() {
        return "DeviceParamSettingsBean{" +
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
