package com.itskylin.common.lib.service.socket.bean.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.Service.socket.bean.msg
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/25 15:28
 */
@SuppressWarnings("all")
public class DeviceStartingupMsgContentBean extends MsgContentBean {
    /**
     * DrugGroupID : 167249
     * PrescriptionID : 133372
     * Doctor :
     * ExecTime : 2018-01-24T18:33:14.173
     * PipeType : 1
     * PipeName : 输液管1号
     * DropCount : 1500
     * DisDropCount : 76
     * Percentage : 5
     * SpeedMax : 160
     * SpeedMin : 72
     * DrugGroup : [{"DrugStr":"马破伤风免疫球蛋白1500iu"}]
     * Status : 4
     * SuffererID : 13298
     * SuffererName : 张玉梅
     * SuffererNo : 80193173
     * SerialNo : 80193173
     * AreaID : 205
     * AreaCode : 90001
     * OfficeID : 449
     * Sex : 女
     * IsCare : 0
     * NursingLevel : 无
     * Age : 23
     * DevCode : null
     */

    @JSONField(name = "DrugGroupID")
    public Integer drugGroupID;
    @JSONField(name = "PrescriptionID")
    public Integer prescriptionID;
    @JSONField(name = "Doctor")
    public String doctor;
    @JSONField(name = "ExecTime")
    public Date execTime;
    @JSONField(name = "PipeType")
    public Integer pipeType;
    @JSONField(name = "PipeName")
    public String pipeName;
    @JSONField(name = "DropCount")
    public Integer dropCount;
    @JSONField(name = "DisDropCount")
    public Integer disDropCount;
    @JSONField(name = "Percentage")
    public Integer percentage;
    @JSONField(name = "SpeedMax")
    public Integer speedMax;
    @JSONField(name = "SpeedMin")
    public Integer speedMin;
    @JSONField(name = "Status")
    public Integer status;
    @JSONField(name = "SuffererID")
    public Integer suffererID;
    @JSONField(name = "SuffererName")
    public String suffererName;
    @JSONField(name = "SuffererNo")
    public String suffererNo;
    @JSONField(name = "SerialNo")
    public String serialNo;
    @JSONField(name = "AreaID")
    public Integer areaID;
    @JSONField(name = "AreaCode")
    public Integer areaCode;
    @JSONField(name = "OfficeID")
    public Integer officeID;
    @JSONField(name = "Sex")
    public String sex;
    @JSONField(name = "IsCare")
    public Integer isCare;
    @JSONField(name = "NursingLevel")
    public String nursingLevel;
    @JSONField(name = "Age")
    public Integer age;
    @JSONField(name = "DevCode")
    public String devCode;
    @JSONField(name = "DrugGroup")
    public List<DrugGroupBean> drugGroup;


    public class DrugGroupBean {
        /**
         * DrugStr : 马破伤风免疫球蛋白1500iu
         */

        @JSONField(name = "DrugStr")
        public String drugStr;
    }

    @Override
    public String toString() {
        return "DeviceStartingupMsgContentBean{" +
                "drugGroupID=" + drugGroupID +
                ", prescriptionID=" + prescriptionID +
                ", doctor='" + doctor + '\'' +
                ", execTime=" + execTime.getTime() +
                ", pipeType=" + pipeType +
                ", pipeName='" + pipeName + '\'' +
                ", dropCount=" + dropCount +
                ", disDropCount=" + disDropCount +
                ", percentage=" + percentage +
                ", speedMax=" + speedMax +
                ", speedMin=" + speedMin +
                ", status=" + status +
                ", suffererID=" + suffererID +
                ", suffererName='" + suffererName + '\'' +
                ", suffererNo='" + suffererNo + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", areaID=" + areaID +
                ", areaCode=" + areaCode +
                ", officeID=" + officeID +
                ", sex='" + sex + '\'' +
                ", isCare=" + isCare +
                ", nursingLevel='" + nursingLevel + '\'' +
                ", age=" + age +
                ", devCode='" + devCode + '\'' +
                ", drugGroup=" + drugGroup +
                '}';
    }
}
