package com.itskylin.common.lib.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>服务端响应的数据类型</p>
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.common.http
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/27 10:31
 */
public class DataType {
    /*返回数据为String*/
    public static final int STRING = 1;
    /*返回数据为xml类型*/
    public static final int XML = 2;
    /*返回数据为json对象*/
    public static final int JSON_OBJECT = 3;

    /**
     * 返回数据类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STRING, XML, JSON_OBJECT})
    public @interface Type {
    }

}
