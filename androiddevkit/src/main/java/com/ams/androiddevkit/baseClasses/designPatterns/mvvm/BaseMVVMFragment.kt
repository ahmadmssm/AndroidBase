package com.ams.androiddevkit.baseClasses.designPatterns.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseMVVMFragment<VM: BaseViewModel<ViewState>, ViewState>(val clazz: KClass<VM>): Fragment() {

    private var viewModel: VM? = null
    //
    private lateinit var lifeCycleRegistry: LifecycleRegistry

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getViewId(), container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = initViewModel()
        //
        lifeCycleRegistry = LifecycleRegistry(this)
        // Set lifecycle aware view model
        // lifecycle.addObserver(viewModel)
        // Custom life cycle observer
        lifeCycleRegistry.addObserver(viewModel!!)
        lifeCycleRegistry.currentState = Lifecycle.State.INITIALIZED
        getViewModel()?.onLifeCycleInitialized()
        onFragmentCreated(savedInstanceState)
        initUI()
        bindViews()
        getViewModel()?.getViewState()?.observe(this, Observer { onViewStateChanged(it) })
    }

    protected open fun initViewModel(): VM {
        // getViewModel(clazz = clazz) { parametersOf(viewModelParams) }
        return getViewModel(clazz = clazz)
    }

    abstract fun getViewId(): Int

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getViewModel(): VM? = viewModel

    protected abstract fun initUI()

    protected abstract fun bindViews()

    protected abstract fun onViewStateChanged(state: ViewState)

    //
    protected open fun onFragmentCreated(savedInstanceState: Bundle?) {
        lifeCycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun onStart() {
        super.onStart()
        lifeCycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onResume() {
        super.onResume()
        lifeCycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onDestroy() {
        super.onDestroy()
        lifeCycleRegistry.currentState = Lifecycle.State.DESTROYED
    }
}