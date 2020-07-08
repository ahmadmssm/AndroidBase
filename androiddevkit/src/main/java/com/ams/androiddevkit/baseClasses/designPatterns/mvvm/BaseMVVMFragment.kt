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
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseMVVMFragment<VM: BaseViewModel<ViewState>, ViewState>(protected val clazz: KClass<VM>): Fragment() {

    private var viewModel: VM? = null
    protected var lifeCycleRegistry: LifecycleRegistry? = null

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(getViewId(), container, false)

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = initViewModel()
        initLifeCycleRegistry()
        onFragmentCreated(savedInstanceState)
        initUI()
        initUI(savedInstanceState)
        bindViews()
        observeStates()
        onViewReady()
    }

    protected open fun initLifeCycleRegistry() {
        lifeCycleRegistry = LifecycleRegistry(this)
        // Set lifecycle aware view model
        // lifecycle.addObserver(viewModel)
        // Custom life cycle observer
        viewModel?.let {
            viewLifecycleOwnerLiveData.hasActiveObservers()
            lifeCycleRegistry?.addObserver(it)
            lifeCycleRegistry?.currentState = Lifecycle.State.INITIALIZED
        }
    }

    protected open fun observeStates() {
        getViewModel()?.let {
            if(!it.getViewState().hasActiveObservers()) {
                it.getViewState().observe(viewLifecycleOwner, Observer { viewState ->
                    onViewStateChanged(viewState)
                })
            }
        }
    }


    protected open fun initViewModel(): VM {
        // getViewModel(clazz = clazz) { parametersOf(viewModelParams) }
        return getViewModel(clazz = clazz)
    }

    abstract fun getViewId(): Int

    protected fun getViewModel(): VM? = viewModel

    protected abstract fun bindViews()

    protected abstract fun initUI()

    protected open fun initUI(bundle: Bundle?) {}

    protected abstract fun onViewStateChanged(state: ViewState)

    protected open fun onViewReady() {}

    //
    protected open fun onFragmentCreated(savedInstanceState: Bundle?) {
        lifeCycleRegistry?.currentState = Lifecycle.State.CREATED
    }

    override fun onStart() {
        super.onStart()
        lifeCycleRegistry?.currentState = Lifecycle.State.STARTED
    }

    override fun onResume() {
        super.onResume()
        lifeCycleRegistry?.currentState = Lifecycle.State.RESUMED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifeCycleRegistry?.currentState = Lifecycle.State.DESTROYED
        viewModel = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}