package com.fcbox.uilib.listaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView



import com.fcbox.uilib.uilib.R

/**
 * Created by 886470 on 16/3/23.
 */
class ActionSheetGreeenListAdapter(var contents : MutableList<String>,var context: Context) : BaseAdapter() {
    private val mLayoutInflater: LayoutInflater
    internal var selectPosition: Int = 0
    private var mOnAdapterItemClickListener: onAdapterItemClickListener? = null


    init {
        mLayoutInflater = LayoutInflater.from(context)
    }

    fun getContentList(): MutableList<String> {

        return contents
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

    fun setSelectPosition(position: Int) {
        this.selectPosition = position
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var tv: TextView? = null
        if (null == view) {
            tv = mLayoutInflater.inflate(R.layout.item_green_listactionsheet, null) as TextView
        } else {
            tv = view as TextView?
        }
        tv!!.text = getContentList()[i]

        val greenTextColor = context.getResources().getColor(R.color.green_lignt3)
        val blackTextColor = context.getResources().getColor(R.color.text0)
        if (i == selectPosition) {
            tv.setTextColor(greenTextColor)
            tv.setBackgroundResource(R.color.gray7)
        } else {
            tv.setTextColor(blackTextColor)
            tv.setBackgroundResource(R.color.white)
        }

        tv.setOnClickListener { v ->
            selectPosition = i
            notifyDataSetChanged()
            mOnAdapterItemClickListener!!.onAdapterItemClick(v, i)
        }
        return tv
    }

    fun setAdapterItemClickListner(listner: onAdapterItemClickListener) {
        this.mOnAdapterItemClickListener = listner
    }

    interface onAdapterItemClickListener {
        fun onAdapterItemClick(view: View, position: Int)
    }

}
