package com.itskylin.common.lib.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.io.Serializable
import java.util.*

/**
 * 通用的適配器
 *
 * @param <T> extends Bean impl Serializable
</T> */
@Suppress("UNCHECKED_CAST")
abstract class BaseListAdapter<T : Serializable>(protected var mContext: Context, datas: List<T>?, private val viewId: Int?) : BaseAdapter() {

    protected var datas: ArrayList<T> = if (datas == null) ArrayList() else ArrayList(datas)
    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    /**
     * 返回数据集合
     *
     * @return
     */
    val items: List<T>
        get() = datas

    /**
     * 添加需要查找的控件ID
     *
     * @return
     */
    abstract val findViewByIDs: IntArray

    /**
     * 插入数据到顶部
     *
     * @param data
     * 数据对象
     */
    protected fun insert(data: T) {
        datas.add(0, data)
        this.notifyDataSetChanged()
    }

    /**
     * 追加数据
     *
     * @param data
     * 数据对象
     */
    fun append(data: T) {
        datas.add(data)
        this.notifyDataSetChanged()
    }

    /**
     * 替换数据
     *
     * @param data
     * 数据对象
     */
    fun replace(data: T) {
        val idx = this.datas.indexOf(data)
        this.replace(idx, data)
    }

    /**
     * 替换指定位置的的数据
     *
     * @param index
     * 索引位置
     * @param data
     * 数据对象
     */
    fun replace(index: Int, data: T) {
        if (index < 0)
            return
        if (index > datas.size - 1)
            return
        datas[index] = data
        this.notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int): Any {
        return datas[position]
    }

    /**
     * 返回指定位置的数据
     *
     * @param position
     * 索引
     * @return
     */
    fun getItemT(position: Int): T {
        return datas[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    /**
     * 删除指定位置的数据
     *
     * @param position
     * 索引
     */
    fun removeItem(position: Int) {
        if (datas.size <= 0)
            return
        if (position < 0)
            return
        if (position > datas.size - 1)
            return
        datas.removeAt(position)
        this.notifyDataSetChanged()
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        datas.clear()
        this.notifyDataSetChanged()
    }

    fun getResourceView(id: Int): View {
        return inflater.inflate(id, null)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        val viewHolder: DataViewHolder
        if (convertView == null) {
            viewHolder = DataViewHolder()
            convertView = inflater.inflate(this!!.viewId!!, null)
            if (convertView == null) {
                return null
            }
            var idAry: IntArray? = this.findViewByIDs
            if (idAry == null) {
                idAry = intArrayOf()
            }
            for (id in idAry) {
                viewHolder.setView(id, convertView.findViewById(id))
            }
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as BaseListAdapter<T>.DataViewHolder
        }
        this.renderData(position, viewHolder)
        return convertView
    }

    abstract fun renderData(position: Int, vh: DataViewHolder)

    inner class DataViewHolder {
        internal var mapView = hashMapOf<Int, View>()
        internal var mapData = hashMapOf<String, Any>()

        fun setView(key: Int, v: View) {
            this.mapView[key] = v
        }

        fun <T> getView(key: Int): T {
            return this.mapView[key] as T
        }

        fun <T> getView(clazz: Class<T>, key: Int): T {
            return this.mapView[key] as T
        }

        fun setData(key: String, value: Any) {
            mapData[key] = value
        }

        fun <T> getData(key: String): T {
            return mapData[key] as T
        }

        fun <T> getData(clazz: Class<T>, key: String): T {
            return mapData[key] as T
        }
    }
}