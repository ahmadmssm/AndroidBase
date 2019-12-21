@file:Suppress("unused")

package com.ams.androiddevkit.utils.extensions

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

inline fun <reified TargetActivity> AppCompatActivity.open(intent: Intent = getIntentBuilder<TargetActivity>(),
                                                           finishThis: Boolean = true) {
    if(finishThis) this.finish()
    this.startActivity(intent)
}

inline fun <reified TargetActivity> AppCompatActivity.open(bundle: Bundle, finishThis: Boolean = true) {
    val activityIntent = getIntentBuilder<TargetActivity>()
    if(finishThis) this.finish()
    activityIntent.putExtras(bundle)
    this.startActivity(activityIntent)
}

inline fun <reified TargetActivity> AppCompatActivity.getIntentBuilder(): Intent {
    return Intent(this, TargetActivity::class.java)
}

fun AppCompatActivity.hideSoftKeyboardFrom(view: View) {
    val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
}

fun AppCompatActivity.getScreenWidthInDP(): Int {
    val configuration: Configuration = this.resources.configuration;
    return configuration.screenWidthDp
}

fun AppCompatActivity.getScreenHeightInDP(): Int {
    val configuration: Configuration = this.resources.configuration;
    return configuration.screenHeightDp
}