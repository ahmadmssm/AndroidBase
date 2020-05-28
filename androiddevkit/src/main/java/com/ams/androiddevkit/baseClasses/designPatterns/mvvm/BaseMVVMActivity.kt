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

@SuppressLint("Registered")
abstract class BaseMVVMActivity<VM: BaseViewModel<ViewState>, ViewState>(val clazz: KClass<VM>): AppCompatActivity() {

    private var viewModel: VM? = null
    //
    private lateinit var lifeCycleRegistry : LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(getViewId())
        viewModel = initViewModel()
        //
        lifeCycleRegistry = LifecycleRegistry(this)
        // Set lifecycle aware view model
        // lifecycle.addObserver(viewModel)
        // Custom life cycle observer
        lifeCycleRegistry.addObserver(viewModel!!)
        lifeCycleRegistry.currentState = Lifecycle.State.INITIALIZED
        getViewModel()?.onLifeCycleInitialized()
        onActivityCreated(savedInstanceState)
        initUI()
        bindViews()
        getViewModel()?.getViewState()?.observe(this, Observer {
            // onViewStateChanged(it)
            it.getContentIfNotHandled()?.let { viewState ->
                // Only proceed if the event has never been handled
                onViewStateChanged(viewState)
            }
        })
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
        viewModel = null
    }
}