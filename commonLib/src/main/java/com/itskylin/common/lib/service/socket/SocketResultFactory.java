package com.itskylin.common.lib.service.socket;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.itskylin.common.lib.service.socket.bean.BaseSocketBean;
import com.itskylin.common.lib.service.socket.bean.ContainerSettingBean;
import com.itskylin.common.lib.service.socket.bean.DeviceBindAndUnbindBean;
import com.itskylin.common.lib.service.socket.bean.DeviceFailedBean;
import com.itskylin.common.lib.service.socket.bean.DeviceParamSettingsBean;
import com.itskylin.common.lib.service.socket.bean.DevicePauseBean;
import com.itskylin.common.lib.service.socket.bean.DeviceShutdownBean;
import com.itskylin.common.lib.service.socket.bean.DeviceStartingupBean;
import com.itskylin.common.lib.service.socket.bean.HeartbeatBean;
import com.itskylin.common.lib.service.socket.bean.InfusionDeleteBean;
import com.itskylin.common.lib.service.socket.bean.InfusionSettingResultBean;
import com.itskylin.common.lib.service.socket.bean.VelocityFlowBean;
import com.itskylin.common.lib.service.socket.bean.VelocityFlowDoneBean;
import com.itskylin.common.lib.service.socket.bean.VoiceSettingBean;

/**
 * <b>Socket消息创建工厂</b><br/>
 * <p>
 * <ul>
 * <li>403：心跳包</li>
 * <li>404：声音设置</li>
 * <li>406：设备暂停</li>
 * <li>407：设备关机</li>
 * <li>410：滴速上下限设置</li>
 * <li>411：设备故障</li>
 * <li>417：输液完成</li>
 * <li>409：设备绑定（老）</li>
 * <li>409：设备开机消息(新)</li>
 * <li>421：输液管设置结果</li>
 * <li>422：容量设置</li>
 * <li>501：设备绑定/解绑（新）</li>
 * <li>502：输液删除</li>
 * <li>499：设备参数设置</li>
 * </ul>
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.Service.socket.
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/22 17:20
 */
@SuppressWarnings("all")
public class SocketResultFactory {

    /**
     * 心跳包
     */
    private static final int STATUS_HEARTBEST = 403;
    /**
     * 声音设置
     */
    private static final int STATUS_VOICE_SETTING = 404;
    /**
     * 设备暂停
     */
    private static final int STATUS_DEVICE_PAUSE = 406;
    /**
     * 设备关机
     */
    private static final int STATUS_DEVICE_SHUTDOWN = 407;
    /**
     * 滴速上下限设置
     */
    private static final int STATUS_DEVICE_LIMIT_FLOW_SETTING = 410;
    /**
     * 设备故障
     */
    private static final int STATUS_DEVICE_FAILED = 411;
    /**
     * 输液完成
     */
    private static final int STATUS_DEVICE_FLOW_DOWN = 417;
    /**
     * 设备开机消息(新)
     */
    private static final int STATUS_DEVICE_STARTINGUP = 409;
    /**
     * 输液管设置结果
     */
    private static final int STATUS_DEVICE_INFUSION_SETTING = 421;
    /**
     * 容量设置
     */
    private static final int STATUS_DEVICE_CONTAINER_SETTING = 422;
    /**
     * 设备绑定/解绑（新）
     */
    private static final int STATUS_DEVICE_BIND = 501;
    /**
     * 输液删除
     */
    private static final int STATUS_DEVICE_INFUSION_DEL = 502;
    /**
     * 设备参数设置
     */
    private static final int STATUS_DEVICE_SETTING = 499;


    /**
     * 创建返回消息工厂
     *
     * @param json
     * @return
     */
    public static BaseSocketBean createMessate(String json) throws JSONException {
        if (TextUtils.isEmpty(json) || json.equals("null")) {
            return null;
        }
        if ("ok".equals(json) || "@heart".equals(json)) {
            return null;
        }


        switch (JSON.parseObject(json).getInteger("MessageType")) {
            case STATUS_HEARTBEST://403
                return JSON.parseObject(json, HeartbeatBean.class);
            case STATUS_VOICE_SETTING://404
                return JSON.parseObject(json, VoiceSettingBean.class);
            case STATUS_DEVICE_PAUSE://406
                return JSON.parseObject(json, DevicePauseBean.class);
            case STATUS_DEVICE_SHUTDOWN://407
                return JSON.parseObject(json, DeviceShutdownBean.class);
            case STATUS_DEVICE_LIMIT_FLOW_SETTING://410
                return JSON.parseObject(json, VelocityFlowBean.class);
            case STATUS_DEVICE_FAILED://411
                return JSON.parseObject(json, DeviceFailedBean.class);
            case STATUS_DEVICE_FLOW_DOWN://417
                return JSON.parseObject(json, VelocityFlowDoneBean.class);
            case STATUS_DEVICE_STARTINGUP://409
                return JSON.parseObject(json, DeviceStartingupBean.class);
            case STATUS_DEVICE_INFUSION_SETTING://421
                return JSON.parseObject(json, InfusionSettingResultBean.class);
            case STATUS_DEVICE_CONTAINER_SETTING://422
                return JSON.parseObject(json, ContainerSettingBean.class);
            case STATUS_DEVICE_BIND://501
                return JSON.parseObject(json, DeviceBindAndUnbindBean.class);
            case STATUS_DEVICE_INFUSION_DEL://502
                return JSON.parseObject(json, InfusionDeleteBean.class);
            case STATUS_DEVICE_SETTING://499
                return JSON.parseObject(json, DeviceParamSettingsBean.class);
        }
        BaseSocketBean baseSocketBean = new BaseSocketBean();
        baseSocketBean.msgContents = json;
        return baseSocketBean;
    }
}
