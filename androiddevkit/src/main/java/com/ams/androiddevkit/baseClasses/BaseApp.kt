package com.ams.androiddevkit.baseClasses

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        //
        injectKoin()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun injectKoin() {
        // Start koin with the modules list
        startKoin {
            androidContext(this@BaseApp)
            modules(getKoinModules())
        }
    }

    protected abstract fun getKoinModules(): List<Module>
}