package ams.androidbase.androidbase.scenes.activites.mainScreenScene

import ams.android_base.baseClasses.designPatterns.mvp.BasePresenter
import ams.androidbase.androidbase.managers.networking.AppRestClient
import io.reactivex.functions.Consumer
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