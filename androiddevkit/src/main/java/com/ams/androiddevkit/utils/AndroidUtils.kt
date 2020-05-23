package com.ams.androiddevkit.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

/**
 * Created by Ahmad Mahmoud on 22-Feb-18.
 */

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class AndroidUtils {

    protected val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
    protected var toast: Toast? = null

    open val isLollipopOrHigher: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    open val isNougatOrHigher: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    open val isOreoOrHigher: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    open val isTenOrHigher: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    open fun convertPixelsToDp(px: Float): Float {
        val dp = px / (metrics.densityDpi / 160f)
        return dp.roundToInt().toFloat()
    }

    open fun convertDpToPixel(dp: Float): Float {
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt().toFloat()
    }

    open fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    open fun hideSoftKeyboard(fragment: Fragment) {
        val inputMethodManager = fragment.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(fragment.requireActivity().currentFocus!!.windowToken, 0)
    }

    open fun setStatusBarColor(activity: Activity, @ColorRes color: Int) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, color)
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

    open fun restartApp(context: Context) {
        val packageManager: PackageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}