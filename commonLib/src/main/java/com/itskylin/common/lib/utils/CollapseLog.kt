package com.itskylin.common.lib.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Thread.UncaughtExceptionHandler
import java.text.SimpleDateFormat
import java.util.*

/**
 * catch program crash log
 * 捕获程序崩溃日志
 *
 * @author sunzhk
 */
class CollapseLog private constructor() : UncaughtExceptionHandler {
    /**
     * 是否需要自动退出程序
     */
    private var autoExit = true
    /**
     * 是否需要自动重启
     */
    private var autoRestart = true
    /**
     * 上下文
     */
    private var mContext: Context? = null
    /**
     * 上传回调
     */
    private var mUploadCallBack: UploadCollapseLog? = null
    /**
     * 用于存储设备信息与异常信息
     */
    private val infos = HashMap<String, String>()
    /**
     * 格式化日期
     */
    private val mDateFormat = SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.CHINA)

    /**
     * @param context
     */
    fun init(context: Context): CollapseLog? {
        init(context, true, null)
        return getInstance()
    }

    /**
     * @param context
     * @param autoExit
     */
    fun init(context: Context, autoExit: Boolean): CollapseLog? {
        init(context, autoExit, null)
        return getInstance()
    }

    /**
     * 获取上下文和回调
     *
     * @param context
     */
    fun init(context: Context, autoExit: Boolean, uploadCallBack: UploadCollapseLog?): CollapseLog? {
        mContext = context
        this.autoExit = autoExit
        mUploadCallBack = uploadCallBack
        return getInstance()
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            //			try {
            //				Thread.sleep(3000);
            //			} catch (Exception e) {
            //				Log.e(TAG, "sleep error : ", e);
            //			}
            //			android.os.Process.killProcess(android.os.Process.myPid());
            //			System.exit(1);
            if (autoExit) {
                exit()
            }
        }
    }

    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        Log.e(TAG, "捕获到的崩溃异常", Exception(ex))
        //收集设备参数信息
        collectDeviceInfo(mContext)

        object : Thread() {

            override fun run() {
                super.run()
                Looper.prepare()
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }.start()
        //保存日志文件
        saveCatchInfo2File(ex)
        return true
    }

    fun collectDeviceInfo(context: Context?) {
        try {
            val packageManager = context!!.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            if (packageInfo != null) {
                val versionName = if (packageInfo.versionName == null) "null" else packageInfo.versionName
                val versionCode = packageInfo.versionCode.toString()
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
            }
        } catch (e: Exception) {
            Log.e(TAG, "an error occured when collect package info", e)
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
                Log.d(TAG, field.name + " : " + field.get(null))
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }

        }
    }

    /**
     * 保存错误信息到文件
     *
     * @param ex
     * @return 返回文件名称
     */
    private fun saveCatchInfo2File(ex: Throwable) {
        val sb = StringBuffer()
        for ((key, value) in infos) {
            sb.append(key)
            sb.append("=")
            sb.append(value)
            sb.append("\n")
        }
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace()
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        try {
            FileUtils.writeLog(sb.toString(), "crash")
        } catch (e: Exception) {
            Log.e(TAG, "an error occured while writing file...", e)
        }

    }

    fun exit() {
        if (autoRestart) {
            setRestart()
        }
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(1)
    }

    private fun setRestart() {
        val alarmManager = mContext!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val packageManager = mContext!!.packageManager
        val intent = packageManager.getLaunchIntentForPackage(mContext!!.packageName)
        val pendingIntent = PendingIntent.getActivity(mContext, 0x000001, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.set(AlarmManager.RTC_WAKEUP, 1000, pendingIntent)
    }

    /**
     * 设置是否需要自动重启
     *
     * @param autoRestart
     */
    fun setAutoRestart(autoRestart: Boolean): CollapseLog? {
        this.autoRestart = autoRestart
        autoExit = autoExit or this.autoRestart
        return getInstance()
    }

    interface UploadCollapseLog {
        fun upload(logPath: String)
    }

    companion object {

        val TAG = "CollapseLog"
        /**
         * 单实例
         */
        private var INSTANCE: CollapseLog? = null

        /**
         * 系统默认处理类
         */
        private var mDefaultHandler: UncaughtExceptionHandler? = null

        //在Kotlin中，我们就用的注释方式来加锁
        @Synchronized
        fun getInstance(): CollapseLog {
            if (INSTANCE == null) {
                INSTANCE = CollapseLog()
                mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
                Thread.setDefaultUncaughtExceptionHandler(INSTANCE)
            }
            return INSTANCE!!
        }

    }

}
