package com.ams.androidkitapplication.scenes.activites.mainScreenScene

import com.ams.androidkitapplication.managers.networking.AppRestClient
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BasePresenter
import io.reactivex.schedulers.Schedulers

class MainScreenPresenter(viewDelegate: MainScreenViewDelegate): BasePresenter<MainScreenViewDelegate>(viewDelegate) {

    fun testAPI() {
        val appRestClient = AppRestClient()
//        appRestClient
//                .apIs
//                .worldCountries
//                .subscribeOn(Schedulers.io())
//                .subscribe({ countriesList ->
//                    println("Hopa" + countriesList.size)
//                    println("Hopa" + countriesList[0].name)
//                }, { throwable ->
//                    throwable.printStackTrace()
//                })
    }
}