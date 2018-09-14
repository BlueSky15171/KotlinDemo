package com.itskylin.common.lib.service.socket.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 422 容量设置
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.testsocket.socket
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 17:14
 */
@SuppressWarnings("all")
public class ContainerSettingBean extends BaseSocketBean {
    /**
     * DropCount : 0
     * SuffererID : 2834
     * DrugGroupID : 16329
     */

    @JSONField(name = "DropCount")
    public int dropCount;
    @JSONField(name = "SuffererID")
    public int suffererID;
    @JSONField(name = "DrugGroupID")
    public int drugGroupID;

    @Override
    public String toString() {
        return "ContainerSettingBean{" +
                "dropCount=" + dropCount +
                ", suffererID=" + suffererID +
                ", drugGroupID=" + drugGroupID +
                ", station=" + station +
                ", macCode=" + macCode +
                ", messageType=" + messageType +
                ", result=" + result +
                ", macAddress='" + macAddress + '\'' +
                ", msgContents='" + msgContents + '\'' +
                '}';
    }
}
