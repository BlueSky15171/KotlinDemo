package com.itskylin.common.lib.service.socket.bean;

/**
 * 407 设备关机
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:36
 */
public class DeviceShutdownBean extends BaseSocketBean {
    @Override
    public String toString() {
        return "DeviceShutdownBean{" +
                "station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
