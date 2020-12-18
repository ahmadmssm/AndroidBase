package com.ams.androidkitapplication.scenes.activites.splashScreenScene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BaseActivity
import com.ams.androidkitapplication.R
import com.ams.androidkitapplication.managers.Navigator
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //
        goHomeButton.setOnClickListener {
            Navigator.openMainActivity()
        }
    }
}
