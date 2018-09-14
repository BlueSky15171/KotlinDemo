package com.itskylin.common.lib.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.os.Environment
import android.util.Log
import android.view.View

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Sky Lin
 * @version V1.0
 * @Package KY_InfusionSys_svn/com.konying.commonlib.utils
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/7/3 14:03
 */
object FileUtils {
    /**
     * InputStrem 转byte[]
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun readStreamToBytes(`in`: InputStream): ByteArray {
        val output = ByteArrayOutputStream()
        try {
            var read: Int = -1
            `in`.use { input ->
                output.use { it ->
                    while (input.read().also { read = it } != -1) {
                        it.write(read)
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            output.flush()
            val result = output.toByteArray()
            output.close()
            `in`.close()
            return result
        }
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    @Throws(IOException::class)
    fun writeFile(`in`: InputStream, file: File) {
        if (!file.parentFile.exists())
            file.parentFile.mkdirs()

        if (file.exists())
            file.delete()
        val output = FileOutputStream(file)
        try {
            var read: Int = -1
            `in`.use { input ->
                output.use { it ->
                    while (input.read().also { read = it } != -1) {
                        it.write(read)
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            output.flush()
            output.close()
            `in`.close()
        }
    }

    /**
     * 得到Bitmap的byte
     *
     * @return
     * @author YOLANDA
     */
    fun bmpToByteArray(bmp: Bitmap?): ByteArray? {
        if (bmp == null)
            return null
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 80, output)

        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun drawable2Bitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        } else if (drawable is NinePatchDrawable) {
            val bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            if (drawable.getOpacity() != PixelFormat.OPAQUE)
                                Bitmap.Config.ARGB_8888
                            else
                                Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight())
            drawable.draw(canvas)
            return bitmap
        } else {
            return null
        }
    }

    /*
     * 根据view来生成bitmap图片，可用于截图功能
     */
    fun getViewBitmap(v: View): Bitmap? {

        v.clearFocus() //

        v.isPressed = false //
        // 能画缓存就返回false

        val willNotCache = v.willNotCacheDrawing()
        v.setWillNotCacheDrawing(false)

        val color = v.drawingCacheBackgroundColor
        v.drawingCacheBackgroundColor = 0

        if (color != 0) {
            v.destroyDrawingCache()
        }

        v.buildDrawingCache()

        val cacheBitmap = v.drawingCache ?: return null

        val bitmap = Bitmap.createBitmap(cacheBitmap)
        // Restore the view

        v.destroyDrawingCache()
        v.setWillNotCacheDrawing(willNotCache)
        v.drawingCacheBackgroundColor = color

        return bitmap
    }


    /**
     * 写日志
     *
     * @param err
     * @param filename
     */
    fun writeLog(err: String, filename: String) {
        val formatter = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS", Locale.CHINA)
        val f = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
        val curDate = Date(System.currentTimeMillis())//获取当前时间
        val str = formatter.format(curDate) + ":"
        val strContent = str + err + "\n"
        try {
            val dir = File(Environment
                    .getExternalStorageDirectory().path
                    + File.separator + "KYLog")
            if (!dir.exists())
                dir.mkdir()
            val file = File(dir.toString() + File.separator + f.format(curDate) + "_" + filename + ".txt")
            if (!file.exists()) {
                file.createNewFile()
            }
            val raf = RandomAccessFile(file, "rw")
            raf.seek(file.length())
            raf.write(strContent.toByteArray())
            raf.close()
        } catch (e: Exception) {
            Log.e("TestFile", "Error on write File.")
        }

    }
}