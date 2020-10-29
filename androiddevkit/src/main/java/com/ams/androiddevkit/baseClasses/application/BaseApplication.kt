package com.ams.androiddevkit.baseClasses.application

import android.app.Application
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.ams.androiddevkit.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
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
    protected open fun initKoin(application: Application, with: (KoinApplication).() -> Unit = {}) {
        startKoin {
            with(this)
            androidLogger(getKoinLoggingLevel())
            fragmentFactory()
            androidContext(application)
            modules(getKoinModules())
        }
    }

    protected open fun initAppLifeCycleObserver(observer: LifecycleObserver) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }

    abstract fun getCurrentActivity(): AppCompatActivity?

    protected abstract fun getKoinLoggingLevel(): Level

    protected abstract fun getKoinModules(): List<Module>
}