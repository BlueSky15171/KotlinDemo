package com.itskylin.common.lib.http.base;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author Sky Lin
 * @version V1.0
 * @Package Persionsys2/com.konying.common.http.base
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/15 10:49
 */
public abstract class BaseResponseBean implements BaseBean {
    @JSONField(name = "Result")
    public int result;
    @JSONField(name = "Message")
    public String message;


    @Override
    public String toString() {
        return "BaseResponseBean{" +
                "result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}