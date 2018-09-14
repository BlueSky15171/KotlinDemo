package com.itskylin.common.lib.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

/**
 * @author Sky Lin
 * @version V1.0
 * @Package KY_InfusionSys_svn/com.konying.commonlib.adapter
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/7/23 10:47
 */
class BaseFragmentPageAdapter(fm: FragmentManager, fragments: List<Fragment>?) : FragmentPagerAdapter(fm) {

    private val mFragmentList: List<Fragment>

    init {
        this.mFragmentList = fragments ?: ArrayList()
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}