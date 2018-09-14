package com.itskylin.common.lib.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.itskylin.common.lib.R;
import com.itskylin.common.lib.utils.CommonUtils;
import com.itskylin.common.lib.utils.NetworkUtils;
import com.itskylin.common.lib.utils.StringUtils;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * <p>Http client utils</p>
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.common.http
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/27 10:32
 */
@SuppressWarnings("all")
public class HttpClient {
    /**
     * 超时时间10s
     */
    private static final long DEFAULT_TIMEOUT = 10;

    /*用户设置的BASE_URL*/
    private static String BASE_URL = "";
    /*本地使用的baseUrl*/
    private String baseUrl = "";
    private static OkHttpClient okHttpClient;
    private Builder mBuilder;
    private Retrofit retrofit;
    private Call<ResponseBody> mCall;
    private static final Map<String, Call> CALL_MAP = new HashMap<>();

    /**
     * 获取HttpClient的单例
     *
     * @return HttpClient的唯一对象
     */
    private static HttpClient getIns() {
        return HttpClientHolder.sInstance;
    }

    /**
     * 单例模式中的静态内部类写法
     */
    private static class HttpClientHolder {
        private static final HttpClient sInstance = new HttpClient();
    }

    private HttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(CommonUtils.Companion.getContext()));

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        request = requestBuilder
                                .addHeader("buildingID", "7")
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                                        bodyToString(request.body())))//关键部分，设置requestBody的编码格式为json
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(logging)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
//                .proxy(Proxy.NO_PROXY)
                .build();
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    private void setBuilder(Builder builder) {
        this.mBuilder = builder;
    }

    /**
     * 获取的Retrofit的实例，
     * 引起Retrofit变化的因素只有静态变量BASE_URL的改变。
     */
    private void getRetrofit() {
        if (!BASE_URL.equals(baseUrl) || retrofit == null) {
            baseUrl = BASE_URL;
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();
        }
    }

//    public void post(final OnResultListener onResultListener) {
//        Builder builder = mBuilder;
//        if (builder.params.size() > 0) {
//            mCall = retrofit.create(ApiService.class)
//                    .postMap(builder.url, builder.params);
//        } else {
//            mCall = retrofit.create(ApiService.class)
//                    .post(builder.url);
//        }
//        putCall(builder, mCall);
//        request(builder, onResultListener, false);
//    }

    public void post(final OnResultListener onResultListener) {
        postFinal(null, onResultListener, false);
    }

    /**
     * 异步
     *
     * @param bean
     * @param onResultListener
     */
//    public void post(Serializable bean, final OnResultListener onResultListener) {
//        Builder builder = mBuilder;
//        mCall = retrofit.create(ApiService.class)
//                .postBean(builder.url, bean);
//        putCall(builder, mCall);
//
//        request(builder, onResultListener, false);
//    }
    public void post(Serializable bean, final OnResultListener onResultListener) {
        postFinal(bean, onResultListener, false);
    }

    /**
     * 异步
     *
     * @param json
     * @param onResultListener
     */
//    public void post(String json, final OnResultListener onResultListener) {
//        Builder builder = mBuilder;
//        mCall = retrofit.create(ApiService.class)
//                .postJson(builder.url, json);
//        putCall(builder, mCall);
//        request(builder, onResultListener, false);
//    }
    public void post(String json, final OnResultListener onResultListener) {
        postFinal(json, onResultListener, false);
    }

    public void postSync(final OnResultListener onResultListener) {
        postFinal(null, onResultListener, true);
    }

    public void postSync(Serializable bean, final OnResultListener onResultListener) {
        postFinal(bean, onResultListener, true);
    }

    public void postSync(String json, final OnResultListener onResultListener) {
        postFinal(json, onResultListener, true);
    }

    private void postFinal(Object object, final OnResultListener onResultListener, boolean isSync) {
        Builder builder = mBuilder;
        if (object == null) {
            if (builder.params.size() > 0) {
                mCall = retrofit.create(ApiService.class)
                        .postMap(builder.url, builder.params);
            } else {
                mCall = retrofit.create(ApiService.class)
                        .post(builder.url);
            }
        } else {
            if (object instanceof String) {
                mCall = retrofit.create(ApiService.class)
                        .postJson(builder.url, (String) object);
            } else if (object instanceof Serializable) {
                mCall = retrofit.create(ApiService.class)
                        .postBean(builder.url, (Serializable) object);
            }
        }
        putCall(builder, mCall);
        request(builder, onResultListener, isSync);
    }


    public void get(final OnResultListener onResultListener) {
        Builder builder = mBuilder;
        if (!builder.params.isEmpty()) {
            String value = "";
            for (Map.Entry<String, String> entry : builder.params.entrySet()) {
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                String span = value.equals("") ? "" : "&";
                String part = StringUtils.buffer(span, mapKey, "=", mapValue);
                value = StringUtils.buffer(value, part);
            }
            builder.url(StringUtils.buffer(builder.url, "?", value));
        }
        mCall = retrofit.create(ApiService.class).executeGet(builder.url);
        putCall(builder, mCall);
        request(builder, onResultListener, false);
    }

    public void getSync(final OnResultListener onResultListener) {
        Builder builder = mBuilder;
        if (builder.params.isEmpty()) {
            String value = "";
            for (Map.Entry<String, String> entry : builder.params.entrySet()) {
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                String span = value.equals("") ? "" : "&";
                String part = StringUtils.buffer(span, mapKey, "=", mapValue);
                value = StringUtils.buffer(value, part);
            }
            builder.url(StringUtils.buffer(builder.url, "?", value));
        }
        mCall = retrofit.create(ApiService.class).executeGet(builder.url);
        putCall(builder, mCall);
        request(builder, onResultListener, true);
    }

    /**
     * 实际请求,支持同步/异步
     *
     * @param builder
     * @param onResultListener
     * @param isSync
     */
    private void request(final Builder builder, final OnResultListener onResultListener, boolean isSync) {
        if (!NetworkUtils.isConnected()) {
            onResultListener.onError(-1, CommonUtils.Companion.getString(R.string.current_internet_invalid));
            return;
        }
        if (isSync) {
            try {
                Response<ResponseBody> execute = mCall.execute();
                final ResponseBody body = execute.body();
                if (body != null) {
                    parseData(body.string(), builder.clazz, builder.bodyType, onResultListener);
                } else {
                    onResultListener.onError(-200, "无数据返回");
                }
            } catch (IOException e) {
                e.printStackTrace();
                onResultListener.onError(-403, e.getMessage());
            }
        } else {
            mCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (200 == response.code()) {
                        try {
                            String result = response.body().string();
                            parseData(result, builder.clazz, builder.bodyType, onResultListener);
                        } catch (IOException | IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!response.isSuccessful() || 200 != response.code()) {
                        onResultListener.onError(response.code(), response.message());
                    }
                    if (null != builder.tag) {
                        removeCall(builder.url);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable e) {
                    e.printStackTrace();
                    if (e instanceof SocketTimeoutException) {
                        onResultListener.onError(-1, "连接超时");
                    } else if (e instanceof ConnectException) {
                        onResultListener.onError(-1, "网络不可用");
                    } else if (e instanceof ConnectTimeoutException) {
                        onResultListener.onError(-1, "连接超时");
                    } else if (e instanceof UnknownHostException) {
                        onResultListener.onError(-1, "连不到服务器");
                    } else {
                        onResultListener.onError(-1, e.getMessage());
                    }
                    if (null != builder.tag) {
                        removeCall(builder.url);
                    }
                }

            });
        }
    }


    /**
     * 添加某个请求
     */
    private synchronized void putCall(Builder builder, Call call) {
        if (builder.tag == null)
            return;
        synchronized (CALL_MAP) {
            CALL_MAP.put(builder.tag.toString() + builder.url, call);
        }
    }


    /**
     * 取消某个界面都所有请求，或者是取消某个tag的所有请求;
     * 如果要取消某个tag单独请求，tag需要传入tag+url
     *
     * @param tag 请求标签
     */
    public synchronized void cancel(Object tag) {
        if (tag == null)
            return;
        List<String> list = new ArrayList<>();
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.startsWith(tag.toString())) {
                    CALL_MAP.get(key).cancel();
                    list.add(key);
                }
            }
        }
        for (String s : list) {
            removeCall(s);
        }

    }

    /**
     * 移除某个请求
     *
     * @param url 添加的url
     */
    private synchronized void removeCall(String url) {
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.contains(url)) {
                    url = key;
                    break;
                }
            }
            CALL_MAP.remove(url);
        }
    }

    /**
     * Build a new HttpClient.
     * url is required before calling. All other methods are optional.
     */
    public static final class Builder {
        private String builderBaseUrl = "";
        private String url;
        private Object tag;
        private Map<String, String> params = new HashMap<>();
        /*返回数据的类型,默认是string类型*/
        @DataType.Type
        private int bodyType = DataType.JSON_OBJECT;
        /*解析类*/
        private Class clazz;

        public Builder() {
        }

        /**
         * 请求地址的baseUrl，最后会被赋值给HttpClient的静态变量BASE_URL；
         *
         * @param baseUrl 请求地址的baseUrl
         */
        public Builder baseUrl(String baseUrl) {
            this.builderBaseUrl = baseUrl;
            return this;
        }

        /**
         * 除baseUrl以外的部分，
         * 例如："mobile/login"
         *
         * @param url path路径
         */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 给当前网络请求添加标签，用于取消这个网络请求
         *
         * @param tag 标签
         */
        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        /**
         * 添加请求参数
         *
         * @param key   键
         * @param value 值
         */
        public Builder params(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        /**
         * 响应体类型设置,如果要响应体类型为STRING，请不要使用这个方法
         *
         * @param clazz 指定的解析类
         * @param <T>   解析类
         */
        public <T extends Serializable> Builder bodyType(@NonNull Class<T> clazz) {
            return bodyType(DataType.JSON_OBJECT, clazz);
        }

        /**
         * 响应体类型设置,如果要响应体类型为STRING，请不要使用这个方法
         *
         * @param bodyType 响应体类型，分别:STRING，JSON_OBJECT,JSON_ARRAY,XML
         * @param clazz    指定的解析类
         * @param <T>      解析类
         */
        public <T extends Serializable> Builder bodyType(@DataType.Type int bodyType, @NonNull Class<T> clazz) {
            this.bodyType = bodyType;
            this.clazz = clazz;
            return this;
        }

        public HttpClient build() {
            if (!TextUtils.isEmpty(builderBaseUrl)) {
                BASE_URL = builderBaseUrl;
            }
            HttpClient client = HttpClient.getIns();
            client.getRetrofit();
            client.setBuilder(this);
            return client;
        }

    }

    /**
     * 数据解析方法
     *
     * @param data             要解析的数据
     * @param clazz            解析类
     * @param bodyType         解析数据类型
     * @param onResultListener 回调方数据接口
     */
    @SuppressWarnings("unchecked")
    private void parseData(String data, Class clazz, @DataType.Type int bodyType, OnResultListener onResultListener) {
        try {
            switch (bodyType) {
                case DataType.STRING:
                    onResultListener.onSuccess(data);
                    break;
                case DataType.XML:
                    onResultListener.onSuccess(DataParseUtils.parseXml(data, clazz));
                    break;
                case DataType.JSON_OBJECT:
                default:
                    onResultListener.onSuccess(JSON.parseObject(data, clazz));
                    break;
            }
        } catch (Exception e) {
            onResultListener.onError(-200, "返回数据格式化出错,请检查返回数据");
        }
    }


    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            Buffer buffer = new Buffer();
            if (copy != null) copy.writeTo(buffer);
            else return "";
            return URLDecoder.decode(buffer.readUtf8(), "UTF-8");
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
