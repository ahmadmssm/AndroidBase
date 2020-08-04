package com.ams.androidkitapplication.scenes.activites.mainScreenScene

import com.ams.androidkitapplication.managers.networking.AppRestClient
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BasePresenter

class MainScreenPresenter(viewDelegate: MainScreenViewDelegate): BasePresenter<MainScreenViewDelegate>(viewDelegate) {

    fun testAPI() {
        val appRestClient = AppRestClient()
//        appRestClient
//                .apIs
//                .worldCountries
//                .subscribeOn(Schedulers.io())
//                .subscribe({ countriesList ->
//                    println("Size" + countriesList.size)
//                    println("Name" + countriesList[0].name)
//                }, { throwable ->
//                    throwable.printStackTrace()
//                })
    }
}