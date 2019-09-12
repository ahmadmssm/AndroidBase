package com.ams.androiddevkit.baseClasses

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class BaseApp<App: Application>: Application() {

    override fun onCreate() {
        super.onCreate()
        //
        injectKoin()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun injectKoin() {
        // Start koin with the modules list
        startKoin {
            androidLogger()
            androidContext(getAndroidContext())
            modules(getKoinModules())
        }
    }

    protected abstract fun getAndroidContext(): Context;

    protected abstract fun getKoinModules(): List<Module>
}