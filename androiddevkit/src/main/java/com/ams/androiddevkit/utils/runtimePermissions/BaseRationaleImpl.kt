package com.ams.androiddevkit.utils.runtimePermissions

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.yanzhenjie.permission.RequestExecutor

@Suppress("MemberVisibilityCanBePrivate")
open class BaseRationaleImpl<T>: BaseRationale<T> {

    protected var okButtonTitle: String? = null
    protected var cancelButtonTitle: String? = null
    protected var permissionDialogTitle: String? = null
    protected var permissionDescription: String? = null

    override fun showDialog(context: Context, executor: RequestExecutor) {
        getCustomDialog(context, executor).show()
    }

    fun setOkButtonTitle(okButtonTitle: String?): BaseRationaleImpl<T> {
        this.okButtonTitle = okButtonTitle
        return this
    }

    fun setCancelButtonTitle(cancelButtonTitle: String?): BaseRationaleImpl<T> {
        this.cancelButtonTitle = cancelButtonTitle
        return this
    }

    fun setPermissionDialogTitle(permissionDialogTitle: String?): BaseRationaleImpl<T> {
        this.permissionDialogTitle = permissionDialogTitle
        return this
    }

    fun setPermissionDescription(permissionDescription: String?): BaseRationaleImpl<T> {
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