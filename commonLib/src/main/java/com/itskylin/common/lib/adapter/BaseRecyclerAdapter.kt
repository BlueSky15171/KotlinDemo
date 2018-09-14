package com.itskylin.common.lib.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

abstract class BaseRecyclerAdapter<T>(mContext: Context, protected var mDatas: MutableList<T>, private val mLayoutId: Int) : RecyclerView.Adapter<BaseRecyclerAdapter.RVBaseViewHolder>() {
    protected var mInflater: LayoutInflater

    init {
        this.mInflater = LayoutInflater.from(mContext)
    }

    fun add(t: T) {
        mDatas.add(0, t)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVBaseViewHolder {
        // 先inflate数据
        val itemView = mInflater.inflate(mLayoutId, parent, false)
        // 返回ViewHolder
        return RVBaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RVBaseViewHolder, position: Int) {
        convert(holder, mDatas[position], position)
    }

    /**
     * 利用抽象方法回传出去，每个不一样的Adapter去设置
     *
     * @param item     当前的数据
     * @param position 当前index
     */
    abstract fun convert(holder: RVBaseViewHolder, item: T, position: Int)

    override fun getItemCount(): Int {
        return mDatas.size
    }

    fun getData(index: Int): T {
        if (mDatas.size > index)
            return mDatas.get(index)
        else {
            return null as T
        }
    }

    fun remove(item: T) {
        val indexOf = mDatas.indexOf(item)
        mDatas.remove(item)
        notifyItemChanged(indexOf)
    }


    class RVBaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // 用来存放子View减少findViewById的次数
        private val mViews: SparseArray<View> = SparseArray()

        /**
         * 设置TextView文本
         */
        fun setText(viewId: Int, text: String): RVBaseViewHolder {
            val tv = getView<TextView>(viewId)
            tv.text = text
            return this
        }

        /**
         * 通过id获取view
         */
        fun <T : View> getView(viewId: Int): T {
            // 先从缓存中找
            var view: View? = mViews.get(viewId)
            if (view == null) {
                // 直接从ItemView中找
                view = itemView.findViewById(viewId) as T
                mViews.put(viewId, view)
            }
            return view as T
        }

        /**
         * 设置View的Visibility
         */
        fun setViewVisibility(viewId: Int, visibility: Int): RVBaseViewHolder {
            getView<View>(viewId).visibility = visibility
            return this
        }

        /**
         * 设置ImageView的资源
         */
        fun setImageResource(viewId: Int, resourceId: Int): RVBaseViewHolder {
            val imageView = getView<ImageView>(viewId)
            imageView.setImageResource(resourceId)
            return this
        }

        /**
         * 设置条目点击事件
         */
        fun setOnIntemClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }

        /**
         * 设置条目长按事件
         */
        fun setOnIntemLongClickListener(listener: View.OnLongClickListener) {
            itemView.setOnLongClickListener(listener)
        }

        /**
         * 设置图片通过路径,这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
         * 也可以直接写死
         */
        fun setImageByUrl(viewId: Int, imageLoader: HolderImageLoader?): RVBaseViewHolder {
            val imageView = getView<ImageView>(viewId)
            if (imageLoader == null) {
                throw NullPointerException("imageLoader is null!")
            }
            imageLoader.displayImage(imageView.context, imageView, imageLoader.imagePath)
            return this
        }

        /**
         * 图片加载，这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
         * 也可以不写这个类
         */
        abstract class HolderImageLoader(val imagePath: String) {

            abstract fun displayImage(context: Context, imageView: ImageView, imagePath: String)
        }
    }
}

