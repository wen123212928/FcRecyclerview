package com.fcbox.uilib.listaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


import com.fcbox.uilib.uilib.R

/**
 * @创建者 000778
 * @创建时间 2017/7/31
 * @描述
 */

class ActionSheetGridAdapter(var context: Context, public val contentList: List<String>) : BaseAdapter() {
    private val mLayoutInflater: LayoutInflater
    internal var selectPosition: Int = 0
    private var mOnAdapterItemClickListener: onAdapterItemClickListener? = null

    init {
        mLayoutInflater = LayoutInflater.from(context)
    }

    fun setSelectPosition(position: Int) {
        this.selectPosition = position
    }

    override fun getCount(): Int {
        return contentList.size
    }

    override fun getItem(position: Int): Any {
        return contentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tv: TextView? = null
        if (null == convertView) {
            tv = mLayoutInflater.inflate(R.layout.item_invoice_type, null) as TextView
        } else {
            tv = convertView as TextView?
        }

        tv!!.text = contentList[position]
        //状态扭转
        if (position == selectPosition) {
            changeViewState(tv, true)
        } else {
            changeViewState(tv, false)
        }

        tv.setOnClickListener { v ->
            selectPosition = position
            notifyDataSetChanged()
            mOnAdapterItemClickListener!!.onAdapterItemClick(position)
        }


        return tv
    }

    fun changeViewState(tv: TextView, isSelect: Boolean) {
        val whiteTextColor = context.getResources().getColor(R.color.white)
        val blackTextColor = context.getResources().getColor(R.color.text4)
        if (isSelect) {
            tv.setTextColor(whiteTextColor)
            tv.setBackgroundResource(R.color.green_lignt3)
        } else {
            tv.setTextColor(blackTextColor)
            tv.setBackgroundResource(R.drawable.shape_frame_gray_6_white)
        }

    }

    interface onAdapterItemClickListener {
        fun onAdapterItemClick(position: Int)
    }

    fun setAdapterItemClickListner(listner: onAdapterItemClickListener) {
        this.mOnAdapterItemClickListener = listner
    }
}
