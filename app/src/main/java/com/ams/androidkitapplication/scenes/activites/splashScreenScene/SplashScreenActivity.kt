package com.ams.androidkitapplication.scenes.activites.splashScreenScene

import android.os.Bundle
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BaseActivity
import com.ams.androidkitapplication.R
import com.ams.androidkitapplication.managers.Navigator
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
