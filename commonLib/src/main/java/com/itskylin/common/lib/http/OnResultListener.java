package com.itskylin.common.lib.http;

/**
 * <p>在Retrofit中接口会导致泛型擦除，所以这里回调使用Class</p>
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.common.http
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/27 10:39
 */
public interface OnResultListener<T>{
    /**
     * 请求成功的情况
     *
     * @param result 需要解析的解析类
     */
    void onSuccess(T result);

    /**
     * 响应成功，但是出错的情况
     *
     * @param code    错误码
     * @param message 错误信息
     */
    void onError(int code, String message);
}
