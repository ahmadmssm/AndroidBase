package com.ams.androiddevkit.baseClasses

import android.app.Application
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.ams.androiddevkit.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

@Suppress("unused")
abstract class BaseApplication: Application(), LifecycleObserver {

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
        // https://stackoverflow.com/questions/52369540/what-is-lifecycle-observer-and-how-to-use-it-correctly
        // Observe app life cycle in application class
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun initKoin() {
        startKoin {
            logger(getKoinLoggingLevel())
            androidContext(getAppContext())
            modules(getKoinModules())
        }
    }

    abstract fun getAppContext(): Application

    abstract fun getCurrentActivity(): AppCompatActivity?

    protected abstract fun getKoinLoggingLevel(): org.koin.core.logger.Logger

    protected abstract fun getKoinModules(): List<Module>

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onAppInResume() {}

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onAppPause() {}
}