package com.itskylin.common.lib.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itskylin.common.lib.BuildConfig;
import com.itskylin.common.lib.utils.LogUtils;

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
import butterknife.Unbinder;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package KY_InfusionSys_svn/com.konying.commonlib.base
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/7/23 10:48
 */
/*public*/ abstract class BaseFragmentABS extends Fragment {
    protected String TAG = getClass().getSimpleName();
    /**
     * Wifi
     */
    protected final static int NETTYPE_WIFI = 0x01;
    protected final static int NETTYPE_CMWAP = 0x02;
    protected final static int NETTYPE_CMNET = 0x03;

    protected BaseFragmentABS mContext;
    protected Resources mRes;
    protected Unbinder unbinder;
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBefore();
    }

    /**
     * 初始化View之前
     */
    protected abstract void initBefore();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mContext = this;
        this.mRes = this.getResources();
        if (null == rootView) {
            rootView = LayoutInflater.from(this.getContext()).inflate(getResourceLayout(), container, false);
        } else {
            // 防止重复加载，出现闪退
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        }
        unbinder = ButterKnife.bind(this, rootView);
        initDatas();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 初始化数据
     */
    protected abstract void initDatas();

    /**
     * 创建View
     *
     * @return
     */
    protected abstract @LayoutRes
    int getResourceLayout();


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
                            getActivity().overridePendingTransition(enterAnimId, exitAnimId);
                        }
                        startActivity(new Intent().setClass(BaseFragmentABS.this.getContext(), cls));
                        getActivity().finish();
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
        intent.setClass(getActivity(), activity);
        this.startActivity(intent);
        if (isColse) {
            getActivity().finish();
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
        intent.setClass(getActivity(), activity);
        this.startActivityForResult(intent, resultCode);
        if (isColse) {
            getActivity().finish();
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
        getActivity().setResult(getActivity().RESULT_OK, intent);
        // 关闭Activity
        getActivity().finish();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }


    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
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