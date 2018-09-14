package com.itskylin.common.lib.service.socket.bean;

/**
 * 406 设备暂停
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:35
 */
@SuppressWarnings("all")
public class DevicePauseBean extends BaseSocketBean {


    @Override
    public String toString() {
        return "DevicePauseBean{" +
                "station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
