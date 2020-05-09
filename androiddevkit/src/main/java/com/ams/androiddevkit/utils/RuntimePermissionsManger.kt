@file:Suppress("unused")

package com.ams.androiddevkit.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ams.androiddevkit.utils.RuntimePermissions.BaseRationale
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Options
import com.yanzhenjie.permission.runtime.PermissionRequest

@Suppress("MemberVisibilityCanBePrivate")
open class RuntimePermissionsManger {

    protected var appCompatActivity: AppCompatActivity? = null
    protected var fragment: Fragment? = null
    protected var okButtonTitle: String? = null
    protected var cancelButtonTitle: String? = null
    protected var permissionDialogTitle: String? = null
    protected var permissionDescription: String? = null
    protected var enableRationalMessage = false
    protected val permissionsRequestOptions: Options by lazy {
        if (appCompatActivity != null) AndPermission.with(appCompatActivity) else AndPermission.with(fragment)
    }

    constructor(appCompatActivity: AppCompatActivity) { this.appCompatActivity = appCompatActivity }

    constructor(fragment: Fragment) { this.fragment = fragment }

    fun setOkButtonTitle(okButtonTitle: String): RuntimePermissionsManger {
        this.okButtonTitle = okButtonTitle
        return this
    }

    fun setCancelButtonTitle(cancelButtonTitle: String): RuntimePermissionsManger {
        this.cancelButtonTitle = cancelButtonTitle
        return this
    }

    fun setPermissionDialogTitle(permissionDialogTitle: String): RuntimePermissionsManger {
        this.permissionDialogTitle = permissionDialogTitle
        return this
    }

    fun setPermissionDescription(permissionDescription: String): RuntimePermissionsManger {
        this.permissionDescription = permissionDescription
        return this
    }

    fun setEnableRationalMessage(enableRationalMessage: Boolean): RuntimePermissionsManger {
        this.enableRationalMessage = enableRationalMessage
        return this
    }

    fun setCustomRational(enableRationalMessage: Boolean): RuntimePermissionsManger {
        this.enableRationalMessage = enableRationalMessage
        return this
    }

    open fun getCustomRationale(): BaseRationale<List<String>>? {
        return if (enableRationalMessage) {
            BaseRationale<List<String>>()
                .setOkButtonTitle(okButtonTitle)
                .setCancelButtonTitle(cancelButtonTitle)
                .setPermissionDialogTitle(permissionDialogTitle)
                .setPermissionDescription(permissionDescription)
        } else null
    }

    open fun getRunTimePermissionsBuilder(vararg permissions: String): PermissionRequest {
        return permissionsRequestOptions
            .runtime()
            .permission(*permissions)
            .rationale(getCustomRationale())
    }

    open fun requestSinglePermission(permission: String, listener: RuntimePermissionsListener) {
        getRunTimePermissionsBuilder(permission)
            .onGranted { listener.onPermissionGranted() }
            .onDenied { listener.onPermissionDenied(permission) }
            .start()
    }

    open fun requestPermissions(vararg permissions: String, listener: RuntimePermissionsListener) {
        getRunTimePermissionsBuilder(*permissions)
            .onGranted { grantedPermissions: List<String> ->
                listener.onPermissionsGranted()
                listener.onPermissionsGranted(grantedPermissions)
            }
            .onDenied { deniedPermissions: List<String> -> listener.onPermissionsDenied(deniedPermissions) }
            .start()
    }

    open fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
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