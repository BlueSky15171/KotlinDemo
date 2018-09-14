/**
 * @Title: MyDataUtils.java
 * @Package com.itskylin.android.kuaidi.business.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author wfc, QQ:335441537
 * @date 2015年8月12日 下午9:45:47
 * @version V1.0
 */
package com.itskylin.common.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.Headers;

/**
 * @author BlueSky QQ:345066543
 * @ClassName: SharedPreferencesUtils
 * @Description: SharedPreferences工具
 * @date 2016年3月3日 上午12:27:54
 */
@SuppressWarnings("all")
public class SharedPreferencesUtils {


    public static void putData(Context context, String fileName, String key, Object val) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, val);
        putData(context, fileName, map);
    }

    /**
     * 保存数据到SharedPreferences.xml
     *
     * @param context
     * @param fileName
     * @param map      设定文件
     * @return void 返回类型
     * @throws
     * @Title: putData
     * @Description: 保存数据到SharedPreferences.xml
     */
    public static void putData(Context context, String fileName, Map<String, Object> map) {
        SharedPreferences sp;

        if (TextUtils.isEmpty(fileName))
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        else
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        Editor edit = sp.edit();
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                edit.putString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                edit.putBoolean(entry.getKey(), (Boolean) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                edit.putInt(entry.getKey(), (Integer) entry.getValue());
            } else if (entry.getValue() instanceof Float) {
                edit.putFloat(entry.getKey(), (Float) entry.getValue());
            } else if (entry.getValue() instanceof Long) {
                edit.putLong(entry.getKey(), (Long) entry.getValue());
            }
        }
        edit.commit();
    }

    /**
     * 获取SharedPreferences.xml的数据
     *
     * @param context
     * @param fileName
     * @param key
     * @param clazz
     * @param 设定文件
     * @return Object 返回类型
     * @throws
     * @Title: getData
     * @Description: 获取SharedPreferences.xml的数据
     */
    public static Object getData(Context context, String fileName, String key, Class clazz) {
        SharedPreferences sp;

        if (TextUtils.isEmpty(fileName))
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        else
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if (clazz.getName().equals(String.class.getName())) {
            return sp.getString(key, "");
        } else if (clazz.getName().equals(Boolean.class.getName())) {
            return sp.getBoolean(key, false);
        } else if (clazz.getName().equals(Float.class.getName())) {
            return sp.getFloat(key, 0);
        } else if (clazz.getName().equals(Long.class.getName())) {
            return sp.getLong(key, 0);
        } else {
            return sp.getInt(key, 0);
        }
    }

    public static <T> T getObject(Context context, String fileName, String key, Class<T> clazz) throws Exception {
        SharedPreferences sp;

        if (TextUtils.isEmpty(fileName))
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        else
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 删除某个设置
     *
     * @param context
     * @param key
     */
    public static void removeData(Context context, String fileName, String key) {
        if (context == null || key == null) {
            return;
        }
        SharedPreferences sp;

        if (TextUtils.isEmpty(fileName))
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        else
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * @param context
     * @param cOOKIE_FILE
     * @param headers     设定文件
     * @return void 返回类型
     * @throws
     * @Title: saveData
     * @Description: 获取头信息保存cookie信息到SharedPreferences.xml
     */
    public static void saveHeaders(Context context, Headers headers) throws UnsupportedEncodingException {
        if (headers == null) {
            LogUtils.INSTANCE.d("cookie save failure");
            return;
        }
        Headers responseHeaders = headers;
        for (int i = 0; i < headers.size(); i++) {
            if (headers.value(i).contains("jd_user_id")) {
                String juidstr = headers.value(i).substring(headers.value(i).indexOf("=") + 1, headers.value(i).indexOf(";"));
                // LogUtils.e(URLDecoder.decode(juidstr, "UTF-8"));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user_id", URLDecoder.decode(juidstr, "UTF-8"));
                SharedPreferencesUtils.putData(context, CommonUtils.Companion.getCOOKIES_File(), map);
            }
        }
    }
}