package com.itskylin.common.lib.service.socket.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.itskylin.common.lib.service.socket.bean.msg.DeviceStartingupMsgContentBean;

/**
 * 409 设备开机上线
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:59
 */
@SuppressWarnings("all")
public class DeviceStartingupBean extends BaseSocketBean {

    /**
     * 返回字段"{}"
     *
     * @return
     */
    public DeviceStartingupMsgContentBean msgContent() {
        String msgContent = formatMsgContent();
        if (!TextUtils.isEmpty(msgContent)) {
            return JSON.parseObject(msgContent, DeviceStartingupMsgContentBean.class);
        }
        return null;
    }

    @Override
    public String toString() {
        return "DeviceStartingupBean{" +
                "station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
