package com.ams.androidkitapplication.scenes.activites.mainScreenScene

import com.ams.androidkitapplication.managers.networking.AppRestClient
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BasePresenter
import io.reactivex.schedulers.Schedulers

class MainScreenPresenter(viewDelegator: MainScreenViewDelegator) : BasePresenter<MainScreenViewDelegator>(viewDelegator) {

    fun testAPI() {
        val appRestClient = AppRestClient()
        appRestClient
                .apIs
                .worldCountries
                .subscribeOn(Schedulers.io())
                .subscribe({ countriesList ->
                    println("Hopa" + countriesList.size)
                    println("Hopa" + countriesList[0].name)
                }, { throwable ->
                    throwable.printStackTrace()
                })
    }
}