package ams.androidbase.androidbase.scenes.splashScreenScene

import ams.android_base.baseClasses.mvp.BaseActivity
import ams.androidbase.androidbase.*
import ams.androidbase.androidbase.scenes.mainScreenScene.MainActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SplashScreenActivity: BaseActivity<SplashScreenPresenter>(), SplashScreenViewDelegator {

    @BindView(R.id.goHomeButton)
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Preference_SharedPreference userProfile
    }

    override fun getLayout(): Int { return R.layout.activity_splash_screen; }

    override fun initPresenter(): SplashScreenPresenter { return SplashScreenPresenter(this); }

    @OnClick(R.id.goHomeButton)
    fun goHome () {
//        val intent: Intent = Intent(this, MainActivity::class.java);
//        startActivity(intent);

        val objGson: Gson = bb().getCustomGsonConverter("EEE MMM DD HH:mm:ss z:00 yyyy", "yyyy-MM-dd");


        val objPerson = Person("Mike", "harvey", Date(), "00186")

        // Convert Person object to json
        val json = objGson.toJson(objPerson)
        println("1. Convert Person object to Json")
        println(json)

        // Convert to json to person object
        println("2. Convert JSON to person object")
        val objFromJson = objGson.fromJson(json, Person::class.java)
        System.out.println(objFromJson)
    }

}



