package com.itskylin.common.lib.service.socket.bean;

/**
 * 417 输液完成
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket.device
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 16:41
 */
public class VelocityFlowDoneBean extends BaseSocketBean {
    @Override
    public String toString() {
        return "VelocityFlowDoneBean{" +
                "station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
