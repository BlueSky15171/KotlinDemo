package com.itskylin.common.lib.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.common.http.utils
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/27 09:58
 */
@SuppressWarnings("all")
public class DataParseUtils {

    private DataParseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 解析json数组为List
     *
     * @param json  要解析的json
     * @param clazz 解析类
     * @return List
     */
    public static <T extends Serializable> List<T> parseToList(String json, Class<T[]> clazz) {
        return (List<T>) JSON.parseArray(json, clazz);
    }


    /**
     * 解析Xml格式数据
     *
     * @param json  要解析的json
     * @param clazz 解析类
     */
    public static <T extends Serializable> T parseXml(String json, Class<T> clazz) {
        try {
            if (!TextUtils.isEmpty(json) && clazz != null) {
                Serializer serializer = new Persister();
                InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(json.getBytes("UTF-8")), "utf-8");
                return serializer.read(clazz, is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
