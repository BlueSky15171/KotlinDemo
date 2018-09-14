package com.itskylin.common.lib.service.socket.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 502 输液删除
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 17:17
 */
@SuppressWarnings("all")
public class InfusionDeleteBean extends BaseSocketBean {
    /**
     * DrugGroupID : 167249
     * DeviceMac : 86E99FFEFF570B00
     * SuffererID : 13298
     */

    @JSONField(name = "DrugGroupID")
    public int drugGroupID;
    @JSONField(name = "DeviceMac")
    public String deviceMac;
    @JSONField(name = "SuffererID")
    public int suffererID;

    @Override
    public String toString() {
        return "InfusionDeleteBean{" +
                "drugGroupID=" + drugGroupID +
                ", deviceMac='" + deviceMac + '\'' +
                ", suffererID=" + suffererID +
                ", station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
