@file:Suppress("unused")

package com.ams.androiddevkit.utils.extensions

import android.content.Intent
import android.os.Bundle
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