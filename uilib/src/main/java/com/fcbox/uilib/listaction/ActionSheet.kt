package com.fcbox.uilib.listaction

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.DisplayMetrics
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.TranslateAnimation

/**
 * Created by zhangwf on 18/1/2.
 */
open class ActionSheet(context: Context) {
    private val mDialog: Dialog?
    private var contentView: View? = null
    protected var mDisplay: Display
    private val mLayoutInflater: LayoutInflater

    var showAnim: android.view.animation.Animation? = null
    var hideAnim: android.view.animation.Animation? = null
    private val dismissListener: DialogInterface.OnDismissListener? = null

    val context: Context
        get() = mLayoutInflater.context

    init {
        mLayoutInflater = LayoutInflater.from(context)

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mDisplay = windowManager.defaultDisplay

        mDialog = Dialog(mLayoutInflater.context)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = mDialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setGravity(Gravity.LEFT or Gravity.BOTTOM)
        val vglp = window.attributes
        vglp.x = 0
        vglp.y = 0
        window.attributes = vglp
    }

    fun setContentView(view: View) {
        contentView = view

        val displayMetrics = DisplayMetrics()
        mDisplay.getMetrics(displayMetrics)
        //最小宽度为屏幕宽度
        contentView!!.minimumWidth = displayMetrics.widthPixels

        if (null != contentView) {
            mDialog!!.setContentView(contentView!!)
        }
    }

    fun show() {
        if (null != mDialog) {
            mDialog.show()

            if (null == showAnim) {
                showAnim = Animation.BOTTOM_IN.anim
            }
            contentView!!.startAnimation(showAnim)
        }
    }

    fun setDismissListener(dismissListener: DialogInterface.OnDismissListener) {
        mDialog?.setOnDismissListener(dismissListener)
    }

    fun dismiss(needAnim: Boolean) {
        if (needAnim) {
            dismiss()
        } else {
            mDialog!!.dismiss()
        }
    }

    fun dismiss() {
        if (null != mDialog) {
            if (null == hideAnim) {
                hideAnim = Animation.BOTTOM_OUT.anim
            }
            hideAnim!!.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(animation: android.view.animation.Animation) {

                }

                override fun onAnimationEnd(animation: android.view.animation.Animation) {
                    mDialog.dismiss()
                }

                override fun onAnimationRepeat(animation: android.view.animation.Animation) {

                }
            })
            contentView!!.startAnimation(hideAnim)
        }
    }

    enum class Animation private constructor(public val anim: android.view.animation.Animation) {
        BOTTOM_IN(TranslateAnimation(android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 1.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0f)),
        BOTTOM_OUT(TranslateAnimation(android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 1.0f));

        init {
            this.anim.fillAfter = true
            this.anim.duration = 200
            this.anim.interpolator = FastOutSlowInInterpolator()
        }
    }
}