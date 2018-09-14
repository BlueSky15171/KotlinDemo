package com.itskylin.common.lib.service.socket.bean.msg;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.Service.socket.bean.msg
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/25 14:35
 */
@SuppressWarnings("all")
public class HeartBeatMsgContentBean extends MsgContentBean {

    /**
     * speed : 0
     * workstatus : 3
     * electricity : 75
     * totalCount : 0
     * Maxspeed : 200
     * Minspeed : 6
     * Beepstatus : 0
     */

    @JSONField(name = "speed")
    public Integer speed;
    @JSONField(name = "workstatus")
    public Integer workStatus;
    @JSONField(name = "electricity")
    public Integer electriCity;
    @JSONField(name = "totalCount")
    public Integer totalCount;
    @JSONField(name = "Maxspeed")
    public Integer maxSpeed;
    @JSONField(name = "Minspeed")
    public Integer minSpeed;
    @JSONField(name = "Beepstatus")
    public Integer beepStatus;

    @Override
    public String toString() {
        return "HeartBeatMsgContentBean{" +
                "speed=" + speed +
                ", workStatus=" + workStatus +
                ", electriCity=" + electriCity +
                ", totalCount=" + totalCount +
                ", maxSpeed=" + maxSpeed +
                ", minSpeed=" + minSpeed +
                ", beepStatus=" + beepStatus +
                '}';
    }
}
