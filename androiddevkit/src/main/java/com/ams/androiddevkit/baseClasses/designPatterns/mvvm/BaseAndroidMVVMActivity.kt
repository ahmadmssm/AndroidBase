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
import org.koin.android.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import java.util.*
import kotlin.reflect.KClass

@SuppressLint("Registered")
abstract class BaseAndroidMVVMActivity<VM: BaseAndroidViewModel<ViewState>, ViewState>(protected val clazz: KClass<VM>): AppCompatActivity() {
    // Lazy Inject ViewModel
    private lateinit var viewModel: VM
    //
    private lateinit var lifeCycleRegistry : LifecycleRegistry
    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var viewModelClassType: KClass<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewId())
        @Suppress("UNCHECKED_CAST")
        viewModel = initViewModel()
        //
        lifeCycleRegistry = LifecycleRegistry(this)
        // Set lifecycle aware view model
        // lifecycle.addObserver(viewModel)
        // Custom life cycle observer
        lifeCycleRegistry.addObserver(viewModel)
        lifeCycleRegistry.currentState = Lifecycle.State.INITIALIZED
        getViewModel().onLifeCycleInitialized()
        onActivityCreated(savedInstanceState)
        initUI()
        bindViews()
        getViewModel().getViewState().observe(this, Observer { onViewStateChanged(it) })
    }

    protected open fun initViewModel(): VM {
        // getViewModel(clazz = clazz) { parametersOf(viewModelParams) }
        return getViewModel(clazz = viewModelClassType)
    }

    abstract fun getViewId(): Int

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getViewModel(): VM {
        return viewModel
    }

    protected abstract fun initUI()

    protected abstract fun bindViews()
    
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
    }
}


