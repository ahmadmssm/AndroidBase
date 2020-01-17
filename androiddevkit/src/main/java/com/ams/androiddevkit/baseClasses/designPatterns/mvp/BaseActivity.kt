package com.ams.androiddevkit.baseClasses.designPatterns.mvp

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.ams.androiddevkit.utils.RuntimePermissionsManger

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<Presenter: BasePresenter<*>>: AppCompatActivity(), BaseView {

    private var lifeCycleRegistry: LifecycleRegistry? = null
    protected var runtimePermissionsManger: RuntimePermissionsManger? = null
    protected var presenter: Presenter? = null
        private set

    @get:LayoutRes
    protected abstract val layout: Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        runtimePermissionsManger = RuntimePermissionsManger(this)
        presenter = initPresenter()
        // Custom life cycle observer
        lifeCycleRegistry = LifecycleRegistry(this)
        // Set lifecycle aware presenter
        lifeCycleRegistry?.addObserver(presenter!!)
        lifeCycleRegistry?.currentState = Lifecycle.State.INITIALIZED
        presenter?.onLifeCycleInitialized()
        lifeCycleRegistry?.currentState = Lifecycle.State.CREATED
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    @CallSuper
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifeCycleRegistry?.currentState = Lifecycle.State.STARTED
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifeCycleRegistry?.currentState = Lifecycle.State.RESUMED
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        lifeCycleRegistry?.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifeCycleRegistry?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter = null
        lifeCycleRegistry?.currentState = Lifecycle.State.DESTROYED
        runtimePermissionsManger = null
        presenter = null
        lifeCycleRegistry = null
    }

    protected open fun getContext(): Context? { return this }

    protected abstract fun initPresenter(): Presenter
}