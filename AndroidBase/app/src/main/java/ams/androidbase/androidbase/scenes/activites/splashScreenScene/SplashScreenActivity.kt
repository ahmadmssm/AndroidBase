package ams.androidbase.androidbase.scenes.activites.splashScreenScene

import ams.android_base.baseClasses.mvp.BaseActivity
import ams.androidbase.androidbase.*
import ams.android_base.utils.RuntimePermissionsManger
import android.Manifest
import android.os.Bundle
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick

class SplashScreenActivity: BaseActivity<SplashScreenPresenter>(), SplashScreenViewDelegator {

    @BindView(R.id.goHomeButton)
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Preference_SharedPreference userProfile
    }

    override fun getLayout(): Int { return R.layout.activity_splash_screen; }

    override fun initPresenter(): SplashScreenPresenter { return SplashScreenPresenter(this) }

    @OnClick(R.id.goHomeButton)
    fun goHome () {
        // Navigator.openMainActivity()
        //
        permission.requestSinglePermission(Manifest.permission.CAMERA, object : ams.android_base.utils.RuntimePermissionsManger.RuntimePermissionsListener {
            override fun onPermissionGranted() {
                println("Hopa 1")
            }
            override fun onPermissionDenied(deniedPermission: String) {
                println("Hopa 2")

            }
        })
    }


}
