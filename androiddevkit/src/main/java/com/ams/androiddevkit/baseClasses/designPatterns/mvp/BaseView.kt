package com.ams.androiddevkit.baseClasses.designPatterns.mvp

interface BaseView {
    fun showLoading() {}
    fun hideLoading() {}
    fun onGoOnline() {}
    fun onGoOffline() {}
    fun onLogOut() {}
}