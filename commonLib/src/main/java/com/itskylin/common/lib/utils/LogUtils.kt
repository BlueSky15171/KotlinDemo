@file:Suppress("NAME_SHADOWING")

package com.itskylin.common.lib.utils

import android.util.Log

/**
 * @ClassName: LogUtils
 * @Description: 自定义Log工具类
 * @author BlueSky QQ:345066543
 * @date 2016年3月3日 上午12:32:44
 */
object LogUtils {

    /**
     * 是否启动调试模式
     */
    var isDebug = false

    fun d(msg: String) {
        d("DEBUG", msg)
    }

    fun d(tag: String, msg: String) {
        println(Log.DEBUG, tag, msg)
    }

    fun i(msg: String) {
        i("INFO", msg)
    }

    fun i(tag: String, msg: String) {
        println(Log.INFO, tag, msg)
    }

    fun w(msg: String) {
        w("WARNING", msg)
    }

    fun w(tag: String, msg: String) {
        println(Log.WARN, tag, msg)
    }

    fun e(msg: String) {
        e("ERROR", msg)
    }

    fun e(tag: String, msg: String) {
        println(Log.ERROR, tag, msg)
    }

    private fun println(level: Int, tag: String?, msg: String) {
        var tag = tag
        if (isDebug) {
            if (tag == null) {
                when (level) {
                    Log.ASSERT -> tag = "ASSERT"
                    Log.DEBUG -> tag = "DEBUG"
                    Log.INFO -> tag = "INFO"
                    Log.WARN -> tag = "WARNING"
                    Log.ERROR -> tag = "ERROR"
                    Log.VERBOSE -> tag = "VERBOSE"
                }
            }
            Log.println(level, tag, msg)
        }
    }
}