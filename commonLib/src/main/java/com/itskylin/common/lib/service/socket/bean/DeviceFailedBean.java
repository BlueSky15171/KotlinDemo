package com.itskylin.common.lib.service.socket.bean;

/**
 * 411 设备故障
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:39
 */
@SuppressWarnings("all")
public class DeviceFailedBean extends BaseSocketBean {
    @Override
    public String toString() {
        return "DeviceFailedBean{" +
                "station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
