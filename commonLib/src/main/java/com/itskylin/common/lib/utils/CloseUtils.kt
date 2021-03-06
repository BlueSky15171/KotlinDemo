package com.itskylin.common.lib.utils

import java.io.Closeable
import java.io.IOException

/**
 * 关闭相关工具类
 */
class CloseUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * 关闭IO
         *
         * @param closeables closeable
         */
        fun closeIO(vararg closeables: Closeable?) {
            for (closeable in closeables) {
                try {
                    closeable?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * 安静关闭IO
         *
         * @param closeables closeable
         */
        fun closeIOQuietly(vararg closeables: Closeable?) {
            for (closeable in closeables) {
                    try {
                        closeable?.close()
                    } catch (ignored: IOException) {
                    }
            }
        }
    }
}
