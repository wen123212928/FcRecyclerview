package com.fcbox.uilib.fcrecyclerview

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.GridLayoutManager


/**
 * Created by zhangwf on 2017/9/19.
 */
class WrapperAdapter(private var mInnerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var viewFoot: View? = null
    private var viewHead: View? = null

    private var mFootViews: MutableList<View> = mutableListOf()
    private var mHeadViews: MutableList<View> = mutableListOf()

    /**
     * 添加footView
     */
    fun addFootView(view: View) {
        mFootViews.add(view)
    }

    /**
     *添加headView
     */
    fun addHeadView(view: View) {
        mHeadViews.add(view)
    }

    /**
     * 获取footView个数
     */
    fun getFootViewCount(): Int {
        return mFootViews?.size
    }

    /**
     * 获取headView个数
     */
    fun getHeadViewCount(): Int {
        return mHeadViews?.size
    }


    /*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    override fun getItemCount(): Int {
        return mInnerAdapter.itemCount + getHeadViewCount() + getFootViewCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType < mHeadViews.size) {
            var itemView = mHeadViews.get(viewType)
            ViewHolder.createViewHolder(parent?.context!!, itemView)
        } else if (viewType >= mHeadViews.size + mInnerAdapter.itemCount) {
            var itemView = mFootViews.get(viewType - mInnerAdapter.itemCount - mHeadViews.size)
            ViewHolder.createViewHolder(parent?.context!!, itemView)
        } else {
            mInnerAdapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position < mHeadViews.size || position >= mHeadViews.size + mInnerAdapter.itemCount) {

        } else {
            var pos = position - mHeadViews.size
            mInnerAdapter.onBindViewHolder(holder, pos)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView!!.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) < mHeadViews.size ||
                            getItemViewType(position) >= mHeadViews.size + mInnerAdapter.itemCount) manager.spanCount else 1
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        super.onViewAttachedToWindow(holder)
        val layoutParams = holder?.itemView?.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val layoutParams = holder?.itemView?.layoutParams
            if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                if (getItemViewType(holder.layoutPosition) < mHeadViews.size ||
                        getItemViewType(holder.layoutPosition) >= mHeadViews.size + mInnerAdapter.itemCount)
                    layoutParams.isFullSpan = true
            }
        }
    }

}