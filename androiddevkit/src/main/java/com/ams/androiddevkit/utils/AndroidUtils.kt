package com.ams.androiddevkit.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import kotlin.math.roundToInt

/**
 * Created by Ahmad Mahmoud on 22-Feb-18.
 */
open class AndroidUtils {

    protected val metrics = Resources.getSystem().displayMetrics
    protected var toast: Toast? = null

    protected val isLollipopOrHigher: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    protected val isNougatOrHigher: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    open fun convertPixelsToDp(px: Float): Float {
        val dp = px / (metrics.densityDpi / 160f)
        return dp.roundToInt().toFloat()
    }

    open fun convertDpToPixel(dp: Float): Float {
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt().toFloat()
    }

    open fun showToast(context: Context, text: String?, duration: Int) {
        createToast(context, duration)
        toast?.setText(text)
        toast?.show()
    }

    open fun showToast(context: Context, textResourceId: Int, duration: Int) {
        createToast(context, duration)
        toast?.setText(textResourceId)
        toast?.show()
    }

    protected open fun createToast(context: Context, duration: Int) {
        toast?.cancel()
        toast = Toast(context)
        toast?.setGravity(Gravity.CENTER, 0, 0)
        toast?.duration = duration
    }
}