package com.fcbox.uilib.listaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.fcbox.uilib.uilib.R


import java.util.ArrayList

/**
 * Created by 886470 on 16/3/23.
 */
class ActionSheetListAdapter(context: Context, private var contentList: List<String>) : BaseAdapter() {
    private val mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater = LayoutInflater.from(context)
    }

    fun getContentList(): List<String> {
        return contentList
    }

    override fun getCount(): Int {
        return getContentList().size
    }

    override fun getItem(i: Int): Any {
        return getContentList()[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var tv: TextView? = null
        if (null == view) {
            tv = mLayoutInflater.inflate(R.layout.item_listactionsheet, null) as TextView
        } else {
            tv = view as TextView?
        }
        tv!!.text = getContentList()[i]

        if (2 <= count) {
            var resBackground = R.drawable.selector_actionsheet_item_mid
            if (0 == i) {
                resBackground = R.drawable.selector_actionsheet_item_top
            } else if (count - 1 == i) {
                resBackground = R.drawable.selector_actionsheet_item_bottom
            }

            tv.setBackgroundResource(resBackground)
        }

        return tv
    }
}
