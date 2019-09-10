package ams.androidbase.androidbase.scenes.activites.splashScreenScene

import ams.android_base.baseClasses.designPatterns.mvp.BaseActivity
import ams.androidbase.androidbase.*
import ams.androidbase.androidbase.managers.Navigator
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity: BaseActivity<SplashScreenPresenter>(), SplashScreenViewDelegator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        goHomeButton.setOnClickListener {
            Navigator.openMainActivity()
        }
        // Preference_SharedPreference userProfile
    }

    override fun getLayout(): Int { return R.layout.activity_splash_screen; }

    override fun initPresenter(): SplashScreenPresenter { return SplashScreenPresenter(this) }

    /*
    runtimePermissionsManger
        .requestSinglePermission(Manifest.permission.CAMERA, object : ams.android_base.utils.RuntimePermissionsManger.RuntimePermissionsListener {
             override fun onPermissionGranted() {}
            override fun onPermissionDenied(deniedPermission: String) {}
        })
    */
}
