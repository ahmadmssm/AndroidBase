package com.ams.androiddevkit.utils.runtimePermissions

import android.content.Context
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RequestExecutor

interface BaseRationale<T>: Rationale<T> {

    fun showDialog(context: Context, executor: RequestExecutor)

    override fun showRationale(context: Context, data: T, executor: RequestExecutor) {
        showDialog(context, executor)
    }
}