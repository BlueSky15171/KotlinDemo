package com.itskylin.common.lib.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.itskylin.common.lib.BuildConfig;
import com.itskylin.common.lib.utils.LogUtils;
import com.itskylin.common.lib.utils.ReflectionView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package KY_InfusionSys_svn/com.konying.commonlib.base
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/7/23 09:49
 */
/*public*/ abstract class BaseFragmentActivityABS extends FragmentActivity {
    protected String TAG = getClass().getSimpleName();

    /**
     * Wifi
     */
    protected final static int NETTYPE_WIFI = 0x01;
    protected final static int NETTYPE_CMWAP = 0x02;
    protected final static int NETTYPE_CMNET = 0x03;
    protected Context mContext;
    protected Resources mRes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.mRes = this.getResources();
        initBefore();
        ReflectionView.bindLayout(this);
        ButterKnife.bind(this);
        initToolbar();
        initDatas();
    }

    /**
     * 1.initBefore();
     * 2.ReflectionView.bindLayout(this);
     * 3.ButterKnife.bind(this);
     * 4.initToolbar();
     * 5.initDatas();
     * 初始化ToolBar
     */
    protected abstract void initToolbar();

    /**
     * 1.initBefore();
     * 2.ReflectionView.bindLayout(this);
     * 3.ButterKnife.bind(this);
     * 4.initToolbar();
     * 5.initDatas();
     * 加载布局之前
     */
    protected abstract void initBefore();

    /**
     * 1.initBefore();
     * 2.ReflectionView.bindLayout(this);
     * 3.ButterKnife.bind(this);
     * 4.initToolbar();
     * 5.initDatas();
     * 加载布局之后初始化数据
     */
    protected abstract void initDatas();


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 跳转到下一个activity
     *
     * @param cls
     */
    public void gotoActivity(Class<? extends Activity> cls) {
        gotoActivity(cls, 0, 0);
    }

    /**
     * 跳转到下一个activity,带有动画
     *
     * @param cls
     */
    protected void gotoActivity(final Class<? extends Activity> cls, final int enterAnimId, final int exitAnimId) {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (enterAnimId != 0 && exitAnimId != 0) {
                            overridePendingTransition(enterAnimId, exitAnimId);
                        }
                        startActivity(new Intent().setClass(BaseFragmentActivityABS.this, cls));
                        finish();
                    }
                }, 300);
    }

    /**
     * @param activity 需要打开的activity
     * @Description: 无参跳转
     */
    protected void startActivityParams(Class<? extends Activity> activity) {
        startActivityParams(activity, false);
    }

    /**
     * @param activity 需要打开的activity
     * @param isColse  是否关闭当前activity
     * @Description: 跳转后是否关闭当前Activity
     */
    protected void startActivityParams(Class<? extends Activity> activity, boolean isColse) {
        startActivityParams(null, activity, isColse);
    }

    /**
     * @param map      需要传递到新activity的数据
     * @param activity 需要打开的activity
     * @Description: 带参数跳转
     */
    protected void startActivityParams(Map<String, Object> map, Class<? extends Activity> activity) {
        startActivityParams(null, activity, false);
    }

    /**
     * 打开activity
     *
     * @param map
     * @param activity
     */
    protected void startActivityParams(Map<String, Object> map, Class<? extends Activity> activity, boolean isColse) {
        Intent intent = new Intent();
        // 把返回数据存入Intent
        if (map != null) {
            for (String key : map.keySet()) {
                if (map.get(key) instanceof String) {
                    intent.putExtra(key, String.valueOf(map.get(key)));
                } else if (map.get(key) instanceof Long) {
                    intent.putExtra(key, Long.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Double) {
                    intent.putExtra(key, Double.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Integer) {
                    intent.putExtra(key, Integer.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Float) {
                    intent.putExtra(key, Float.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Boolean) {
                    intent.putExtra(key, Boolean.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Serializable) {
                    intent.putExtra(key, (Serializable) map.get(key));
                } else if (map.get(key) instanceof Bundle) {
                    intent.putExtras((Bundle) map.get(key));
                }
            }
        }
        intent.setClass(this, activity);
        this.startActivity(intent);
        if (isColse) {
            finish();
        }
    }

    protected void startActivityForResult(Class<? extends Activity> activity, int resultCode) {
        startActivityForResult(null, activity, resultCode);
    }

    protected void startActivityForResult(Map<String, Object> map, Class<? extends Activity> activity, int resultCode) {
        startActivityForResult(map, activity, resultCode, false);
    }

    /**
     * 打开activity
     *
     * @param map
     * @param activity
     */
    protected void startActivityForResult(Map<String, Object> map, Class<? extends Activity> activity, int resultCode, boolean isColse) {
        Intent intent = new Intent();
        // 把返回数据存入Intent
        if (map != null) {
            for (String key : map.keySet()) {
                if (map.get(key) instanceof String) {
                    intent.putExtra(key, String.valueOf(map.get(key)));
                } else if (map.get(key) instanceof Long) {
                    intent.putExtra(key, Long.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Double) {
                    intent.putExtra(key, Double.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Integer) {
                    intent.putExtra(key, Integer.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Float) {
                    intent.putExtra(key, Float.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Boolean) {
                    intent.putExtra(key, Boolean.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Serializable) {
                    intent.putExtra(key, (Serializable) map.get(key));
                } else if (map.get(key) instanceof Bundle) {
                    intent.putExtras((Bundle) map.get(key));
                }
            }
        }
        intent.setClass(this, activity);
        this.startActivityForResult(intent, resultCode);
        if (isColse) {
            finish();
        }
    }

    /**
     * 返回数据到activity
     *
     * @param map
     */
    protected void setResultActivity(Map<String, Object> map) {
        Intent intent = new Intent();
        // 把返回数据存入Intent
        if (map != null) {
            for (String key : map.keySet()) {
                if (map.get(key) instanceof String) {
                    intent.putExtra(key, String.valueOf(map.get(key)));
                } else if (map.get(key) instanceof Long) {
                    intent.putExtra(key, Long.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Double) {
                    intent.putExtra(key, Double.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Integer) {
                    intent.putExtra(key, Integer.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Float) {
                    intent.putExtra(key, Float.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Boolean) {
                    intent.putExtra(key, Boolean.valueOf(String.valueOf(map.get(key))));
                } else if (map.get(key) instanceof Serializable) {
                    intent.putExtra(key, (Serializable) map.get(key));
                } else if (map.get(key) instanceof Bundle) {
                    intent.putExtras((Bundle) map.get(key));
                }
            }
        }
        // 设置返回数据
        this.setResult(RESULT_OK, intent);
        // 关闭Activity
        this.finish();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */
    public int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    public String getNetworkTypeName() {
        switch (getNetworkType()) {
            case NETTYPE_CMNET:
                return "NET网络";
            case NETTYPE_CMWAP:
                return "WAP网络";
            case NETTYPE_WIFI:
                return "WIFI网络";
            default:
                return "";
        }
    }

    /**
     * @return String mac地址
     * @Title: getLocalMacAddress
     * @Description: 获取wifi mac地址
     */
    public final String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtils.INSTANCE.e(TAG, ex.toString());
        }
        return null;
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return BuildConfig.VERSION_NAME;
        }
    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static long lastClickTime = 0;
    private static long DIFF = 1000;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    protected boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    protected boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ((lastButtonId == buttonId) && (lastClickTime > 0) && (timeD < diff)) {
            LogUtils.INSTANCE.i("isFastDoubleClick", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    protected boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}