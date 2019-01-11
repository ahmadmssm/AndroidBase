package ams.androidbase.androidbase

import ams.android_base.baseClasses.mvp.BaseActivity
import android.os.Bundle

class SplashScreenActivity : BaseActivity<SplashScreenPresenter>(), SplashScreenViewDelegator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int { return R.layout.activity_splash_screen; }

    override fun initPresenter(): SplashScreenPresenter { return SplashScreenPresenter(this); }

    override fun onGoOffline() {
    }

    override fun onLogOut() {
    }

    override fun onGoOnline() {
    }

}



