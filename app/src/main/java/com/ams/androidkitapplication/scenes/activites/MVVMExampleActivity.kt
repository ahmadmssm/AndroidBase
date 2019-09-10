package com.ams.androidkitapplication.scenes.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ams.androidkitapplication.R

@SuppressLint("Registered")
class MVVMExampleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_example)
    }
}
