package com.ams.androiddevkit.utils.RuntimePermissions

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RequestExecutor

open class BaseRationale<T>: Rationale<T> {

    private var okButtonTitle: String? = null
    private var cancelButtonTitle: String? = null
    private var permissionDialogTitle: String? = null
    private var permissionDescription: String? = null

    override fun showRationale(context: Context, data: T, executor: RequestExecutor) {
        getCustomDialog(context, executor).show()
    }

    fun setOkButtonTitle(okButtonTitle: String?): BaseRationale<T> {
        this.okButtonTitle = okButtonTitle
        return this
    }

    fun setCancelButtonTitle(cancelButtonTitle: String?): BaseRationale<T> {
        this.cancelButtonTitle = cancelButtonTitle
        return this
    }

    fun setPermissionDialogTitle(permissionDialogTitle: String?): BaseRationale<T> {
        this.permissionDialogTitle = permissionDialogTitle
        return this
    }

    fun setPermissionDescription(permissionDescription: String?): BaseRationale<T> {
        this.permissionDescription = permissionDescription
        return this
    }

    open fun getCustomDialog(context: Context, executor: RequestExecutor): AlertDialog.Builder {
        val dialog = AlertDialog.Builder(context)
        dialog.setCancelable(true)
        dialog.setTitle(permissionDialogTitle)
        // List<String> permissionNames = Permission.transformText(context, permissions);
        // String message = permissionDescription + "\n" + TextUtils.join("\n", permissionNames);
        dialog.setMessage(permissionDescription)
        dialog.setPositiveButton(okButtonTitle) { _: DialogInterface?, _: Int -> executor.execute() }
        dialog.setNegativeButton(cancelButtonTitle) { _: DialogInterface?, _: Int -> executor.cancel() }
        return dialog
    }
}