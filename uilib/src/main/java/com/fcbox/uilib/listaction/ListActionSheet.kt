package com.fcbox.uilib.listaction

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView

/**
 * Created by 886470 on 16/3/22.
 */
public class ListActionSheet private constructor(context: Context) : ActionSheet(context) {
    protected var rootLayout: LinearLayout
    lateinit  var mListView: ListView
    lateinit  var cancelBtn: Button
    private val scaleDensity: Float

    init {

        val displayMetrics = DisplayMetrics()
        mDisplay.getMetrics(displayMetrics)
        scaleDensity = displayMetrics.scaledDensity

        rootLayout = LinearLayout(context)
        rootLayout.orientation = LinearLayout.VERTICAL
        rootLayout.gravity = Gravity.BOTTOM
        val padd = (8 * scaleDensity + 0.5f).toInt()
        rootLayout.setPadding(padd, 0, padd, padd)
        setContentView(rootLayout)
    }

    private fun initListView() {
        mListView = ListView(context)
        rootLayout.addView(mListView)
    }

    private fun initCancelButton() {
        cancelBtn = Button(context)
        cancelBtn.setTextColor(Color.BLACK)
        cancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        val clp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        clp.topMargin = (8 * scaleDensity + 0.5f).toInt()
        rootLayout.addView(cancelBtn, clp)

        val height = (45 * scaleDensity + 0.5f).toInt()
        cancelBtn.minimumHeight = height
    }

    private fun resetListHeight() {
        val listAdapter = mListView.adapter ?: return

        var totalHeight = 0
        for (i in 0 until listAdapter.count) {
            val view = listAdapter.getView(i, null, mListView)
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            totalHeight += view.measuredHeight
        }
        totalHeight += mListView.dividerHeight * (listAdapter.count - 1)

        val displayMetrics = DisplayMetrics()
        mDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels * 3 / 5
        if (height < totalHeight) {
            val clp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            mListView.layoutParams = clp
            mListView.requestLayout()
        }
    }

    class Builder(context: Context) {
        private val mListActionSheet: ListActionSheet
        private var mListAdapter: ListAdapter? = null
        private var mOnItemClickListener: OnItemClickListener? = null

        private var cancelIsHide = false
        private var cancelStr: String? = null
        private var cancelTextSize: Float = 0.toFloat()
        private var cancelTextColor: Int = 0
        private var cancelBackground: Int = 0
        private var mOnClickListener: OnClickListener? = null

        init {
            mListActionSheet = ListActionSheet(context)
        }

        fun setAdapter(adapter: ListAdapter): Builder {
            mListAdapter = adapter
            return this
        }

        fun setOnItemClickListener(onItemClickListener: OnItemClickListener): Builder {
            mOnItemClickListener = onItemClickListener
            return this
        }

        fun setCancelButton(content: String, onClickListener: OnClickListener): Builder {
            cancelStr = content
            mOnClickListener = onClickListener
            cancelIsHide = false

            return this
        }

        fun setDismissListener(dismissListener: DialogInterface.OnDismissListener): Builder {
            mListActionSheet.setDismissListener(dismissListener)
            return this
        }

        fun setCancelButton(content: String, textSize: Float, textColor: Int, resBackground: Int, onClickListener: OnClickListener): Builder {
            cancelStr = content
            cancelTextSize = textSize
            cancelTextColor = textColor
            cancelBackground = resBackground
            mOnClickListener = onClickListener
            cancelIsHide = false

            return this
        }

        fun hideCancelButton(isHide: Boolean): Builder {
            cancelIsHide = isHide
            return this
        }

        fun build(): ListActionSheet {
            mListActionSheet.initListView()
            mListActionSheet.mListView.adapter = mListAdapter
            mListActionSheet.mListView.setOnItemClickListener { adapterView, view1, i, l ->
                if (null != mOnItemClickListener) {
                    mOnItemClickListener!!.onItemClick(mListActionSheet, view1, i)
                }
                mListActionSheet.dismiss()
            }
            mListActionSheet.resetListHeight()

            if (!cancelIsHide) {
                mListActionSheet.initCancelButton()
                if (!TextUtils.isEmpty(cancelStr)) {
                    mListActionSheet.cancelBtn.text = cancelStr
                }
                if (0f != cancelTextSize) {
                    mListActionSheet.cancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, cancelTextSize)
                }
                if (0 != cancelTextColor) {
                    mListActionSheet.cancelBtn.setTextColor(cancelTextColor)
                }
                if (0 != cancelBackground) {
                    mListActionSheet.cancelBtn.setBackgroundResource(cancelBackground)
                }
                mListActionSheet.cancelBtn.setOnClickListener { view ->
                    if (null != mOnClickListener) {
                        mOnClickListener!!.onClick(mListActionSheet, view)
                    }
                    mListActionSheet.dismiss()
                }
            }
            return mListActionSheet
        }
    }

    fun setDividerColor(divider: Drawable) {
        mListView.divider = divider
    }

    fun setDividerHeight(height: Int) {
        mListView.dividerHeight = height
    }

    interface OnClickListener {
        fun onClick(actionSheet: ListActionSheet, view: View)
    }

    interface OnItemClickListener {
        fun onItemClick(actionSheet: ListActionSheet, view: View, position: Int)
    }
}
