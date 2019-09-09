package ams.android_base.utils;

import android.content.Context;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Options;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

@SuppressWarnings("unused")
public class RuntimePermissionsManger {

    @SuppressWarnings("FieldCanBeLocal")
    private AppCompatActivity appCompatActivity;
    @SuppressWarnings("FieldCanBeLocal")
    private Fragment fragment;
    private String okButtonTitle;
    private String cancelButtonTitle;
    private String permissionDialogTitle;
    private String permissionDescription;
    private Boolean enableRationalMessage = false;

    public RuntimePermissionsManger (AppCompatActivity appCompatActivity) { this.appCompatActivity = appCompatActivity; }
    public RuntimePermissionsManger (Fragment fragment) { this.fragment = fragment; }

    public void setOkButtonTitle(String okButtonTitle) { this.okButtonTitle = okButtonTitle; }
    public void setCancelButtonTitle(String cancelButtonTitle) { this.cancelButtonTitle = cancelButtonTitle; }
    public void setPermissionDescription(String permissionDescription) { this.permissionDescription = permissionDescription; }
    public void setPermissionDialogTitle(String permissionDialogTitle) { this.permissionDialogTitle = permissionDialogTitle; }
    public void setEnableRationalMessage(Boolean enableRationalMessage) { this.enableRationalMessage = enableRationalMessage; }

    private AlertDialog.Builder customDialog (Context context, final RequestExecutor executor) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(true);
        if (permissionDialogTitle != null) dialog.setTitle(permissionDialogTitle);
        // List<String> permissionNames = Permission.transformText(context, permissions);
        // String message = permissionDescription + "\n" + TextUtils.join("\n", permissionNames);
        dialog.setMessage(permissionDescription);
        dialog.setPositiveButton(okButtonTitle, (dialog12, which) -> executor.execute());
        dialog.setNegativeButton(cancelButtonTitle, (dialog1, which) -> executor.cancel());
        return dialog;
    }

    private Rationale customRationale() {
        if (okButtonTitle != null && cancelButtonTitle != null && permissionDescription != null && enableRationalMessage) {
            return (context, data, executor) -> customDialog(context, executor).show();
        }
        return null;
    }

    private Options getPermissionsRequestOptions () {
        Options andPermissionOptions;
        if (appCompatActivity != null) andPermissionOptions = AndPermission.with(appCompatActivity);
        else andPermissionOptions = AndPermission.with(appCompatActivity);
        return andPermissionOptions;
    }

    public void requestSinglePermission (String permission, final RuntimePermissionsListener listener) {
        getPermissionsRequestOptions()
                .runtime()
                .permission(permission)
                .rationale(customRationale())
                .onGranted(grantedPermission -> listener.onPermissionGranted())
                .onDenied(deniedPermission -> listener.onPermissionDenied(permission))
                .start();
    }

    public void requestPermissions (String[] permissions, final RuntimePermissionsListener listener) {
        getPermissionsRequestOptions()
                .runtime()
                .permission(permissions)
                .rationale(customRationale())
                .onGranted(grantedPermissions -> {
                    listener.onPermissionsGranted();
                    listener.onPermissionsDenied(grantedPermissions);
                })
                .onDenied(listener::onPermissionsDenied)
                .start();
    }

    // Listeners
    public interface RuntimePermissionsListener {
        default void onPermissionsGranted() {}
        default void onPermissionsGranted(List<String> grantedPermissions) {}
        default void onPermissionsDenied(List<String> deniedPermissions) {}
        default void onPermissionGranted() {}
        default void onPermissionDenied(String deniedPermission) {}
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
