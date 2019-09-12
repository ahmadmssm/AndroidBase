package com.ams.androiddevkit.baseClasses

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class BaseApp: Application() {

    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun injectKoin() {
        // Start koin with the modules list
        startKoin {
            androidLogger()
            androidContext(getAppContext())
            modules(getKoinModules())
        }
    }

    abstract fun getAppContext(): Context

    protected abstract fun getKoinModules(): List<Module>
}