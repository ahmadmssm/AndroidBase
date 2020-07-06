@file:Suppress("unused")

package com.ams.androiddevkit.baseClasses.designPatterns.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import kotlin.reflect.KClass

import org.koin.androidx.viewmodel.ext.android.getViewModel

@Suppress("MemberVisibilityCanBePrivate")
@SuppressLint("Registered")
abstract class BaseMVVMActivity<VM: BaseViewModel<ViewState>, ViewState>(protected val clazz: KClass<VM>): AppCompatActivity() {

    private var viewModel: VM? = null
    //
    protected lateinit var lifeCycleRegistry : LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        initKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(getViewId())
        viewModel = initViewModel()
        initLifeCycleRegistry()
        onActivityCreated(savedInstanceState)
        initUI()
        initUI(savedInstanceState)
        bindViews()
        observeStates()
        onViewReady()
    }

    protected open fun initKoinFragmentFactory() {
        setupKoinFragmentFactory()
    }

    protected open fun initLifeCycleRegistry() {
        lifeCycleRegistry = LifecycleRegistry(this)
        // Set lifecycle aware view model
        // lifecycle.addObserver(viewModel)
        // Custom life cycle observer
        viewModel?.let {
            lifeCycleRegistry.addObserver(it)
            lifeCycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }
    }

    protected open fun observeStates() {
        getViewModel()?.let {
            if(!it.getViewState().hasActiveObservers()) {
                it.getViewState().observe(this, Observer { viewState ->
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

    protected open fun onViewReady() {}

    protected open fun restartActivity() {
        finish()
        startActivity(intent)
    }

    protected abstract fun onViewStateChanged(state: ViewState)

    // Hide keyboard when pressing outside (For Activites and Fragments)
    // https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
    override fun dispatchTouchEvent(motionEvent: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(motionEvent)
    }

    //
    protected open fun onActivityCreated(savedInstanceState: Bundle?) {
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
        lifeCycleRegistry.removeObserver(viewModel!!)
        viewModel = null
    }
}