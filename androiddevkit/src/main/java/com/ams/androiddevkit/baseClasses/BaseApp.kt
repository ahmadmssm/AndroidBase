package com.ams.androiddevkit.baseClasses

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import com.ams.androiddevkit.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            val threadPolices = StrictMode.ThreadPolicy.Builder()
                 // or .detectAll() for all detectable problems
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
            val vmPolices = StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
            //
            StrictMode.setThreadPolicy(threadPolices)
            StrictMode.setVmPolicy(vmPolices)
        }
        injectKoin()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun injectKoin() {
        startKoin {
            logger(getKoinLoggingLevel())
            androidContext(this@BaseApp)
            modules(getKoinModules())
        }
    }

    abstract fun getAppContext(): Context

    abstract fun getCurrentActivity(): AppCompatActivity?

    protected abstract fun getKoinLoggingLevel(): org.koin.core.logger.Logger

    protected abstract fun getKoinModules(): List<Module>
}