@file:Suppress("unused")

package com.ams.androiddevkit.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Options
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RequestExecutor

class RuntimePermissionsManger {
    private var appCompatActivity: AppCompatActivity? = null
    private var fragment: Fragment? = null
    private var okButtonTitle: String? = null
    private var cancelButtonTitle: String? = null
    private var permissionDialogTitle: String? = null
    private var permissionDescription: String? = null
    private var enableRationalMessage = false

    constructor(appCompatActivity: AppCompatActivity) { this.appCompatActivity = appCompatActivity }

    constructor(fragment: Fragment) { this.fragment = fragment }

    fun setOkButtonTitle(okButtonTitle: String) { this.okButtonTitle = okButtonTitle }

    fun setCancelButtonTitle(cancelButtonTitle: String) { this.cancelButtonTitle = cancelButtonTitle }

    fun setPermissionDescription(permissionDescription: String) { this.permissionDescription = permissionDescription }

    fun setPermissionDialogTitle(permissionDialogTitle: String) { this.permissionDialogTitle = permissionDialogTitle }

    fun setEnableRationalMessage(enableRationalMessage: Boolean) { this.enableRationalMessage = enableRationalMessage }

    private fun customDialog(context: Context, executor: RequestExecutor): AlertDialog.Builder {
        val dialog = AlertDialog.Builder(context)
        dialog.setCancelable(true)
        if (permissionDialogTitle != null) dialog.setTitle(permissionDialogTitle)
        // List<String> permissionNames = Permission.transformText(context, permissions);
        // String message = permissionDescription + "\n" + TextUtils.join("\n", permissionNames);
        dialog.setMessage(permissionDescription)
        dialog.setPositiveButton(okButtonTitle) { _: DialogInterface?, _: Int -> executor.execute() }
        dialog.setNegativeButton(cancelButtonTitle) { _: DialogInterface?, _: Int -> executor.cancel() }
        return dialog
    }

    private fun customRationale(): Rationale<MutableList<String>>? {
        return if (okButtonTitle != null && cancelButtonTitle != null && permissionDescription != null && enableRationalMessage) {
            Rationale { context: Context, _: Any, executor: RequestExecutor -> customDialog(context, executor).show() }
        } else null
    }

    private val permissionsRequestOptions: Options get() {
        return if (appCompatActivity != null) AndPermission.with(appCompatActivity) else AndPermission.with(fragment)
    }

    fun requestSinglePermission(permission: String, listener: RuntimePermissionsListener) {
        permissionsRequestOptions
            .runtime()
            .permission(permission)
            .rationale(customRationale())
            .onGranted { listener.onPermissionGranted() }
            .onDenied { listener.onPermissionDenied(permission) }
            .start()
    }

    fun requestPermissions(permissions: Array<String>, listener: RuntimePermissionsListener) {
        permissionsRequestOptions
            .runtime()
            .permission(*permissions)
            .rationale(customRationale())
            .onGranted { grantedPermissions: List<String> ->
                listener.onPermissionsGranted()
                listener.onPermissionsGranted(grantedPermissions)
            }
            .onDenied { deniedPermissions: List<String> -> listener.onPermissionsDenied(deniedPermissions) }
            .start()
    }

    // Listeners
    interface RuntimePermissionsListener {
        fun onPermissionsGranted() {}
        fun onPermissionsGranted(grantedPermissions: List<String?>?) {}
        fun onPermissionsDenied(deniedPermissions: List<String?>?) {}
        fun onPermissionGranted() {}
        fun onPermissionDenied(deniedPermission: String?) {}
    }

    /* This is how to request one or many permissions (Do not forget to add the permissions in the manifest)
     String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        RuntimePermissionsManger manger = new RuntimePermissionsManger((AppCompatActivity) context);
        manger.setCancelButtonTitle("No");
        manger.setOkButtonTitle("Yes");
        manger.setPermissionDescription("Message");
        manger.setEnableRationalMessage(true);
        manger.requestPermissions(PERMISSIONS, new RuntimePermissionsManger.RuntimePermissionsListener() {
            @Override
            public void onPermissionsGranted() {
            }
            @Override
            public void onPermissionsDenied(String[] deniedPermissions) {
            }
        });
    */
}