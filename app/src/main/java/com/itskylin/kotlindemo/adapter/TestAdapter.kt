package com.itskylin.kotlindemo.adapter

import android.content.Context
import android.view.View
import com.itskylin.common.lib.adapter.BaseRecyclerAdapter
import com.itskylin.kotlindemo.R
import com.itskylin.kotlindemo.db.Student

/**
 * @author Sky Lin
 * @version V1.0
 * @Package demo/com.itskylin.kotlindemo
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/9/14 16:14
 */
class TestAdapter(mContext: Context, mDatas: MutableList<Student>) : BaseRecyclerAdapter<Student>(mContext, mDatas, R.layout.item) {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun convert(holder: RVBaseViewHolder, item: Student, position: Int) {
        holder.setText(R.id.tvId, item.id.toString())
        holder.setText(R.id.tvName, item.name as String)
        val view = holder.getView<View>(R.id.root)
        view.setOnClickListener {
            onItemClickListener?.onItemClick(it, item, position, this.getItemId(position))
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, item: Student, position: Int, id: Long)
    }
}