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
object AndroidUtils {

    private val metrics = Resources.getSystem().displayMetrics
    private var toast: Toast? = null

    val isLollipopOrHigher: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    val isNougatOrHigher: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    fun convertPixelsToDp(px: Float): Float {
        val dp = px / (metrics.densityDpi / 160f)
        return dp.roundToInt().toFloat()
    }

    fun convertDpToPixel(dp: Float): Float {
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt().toFloat()
    }

    fun showToast(context: Context, text: String?, duration: Int) {
        createToast(context, duration)
        toast?.setText(text)
        toast?.show()
    }

    fun showToast(context: Context, textResourceId: Int, duration: Int) {
        createToast(context, duration)
        toast?.setText(textResourceId)
        toast?.show()
    }

    private fun createToast(context: Context, duration: Int) {
        toast?.cancel()
        toast = Toast(context)
        toast?.setGravity(Gravity.CENTER, 0, 0)
        toast?.duration = duration
    }
}