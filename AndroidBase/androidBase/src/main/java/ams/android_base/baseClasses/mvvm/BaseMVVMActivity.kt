package ams.android_base.baseClasses.mvvm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import kotlin.reflect.KClass

import org.koin.android.viewmodel.ext.android.getViewModel


@SuppressLint("Registered")
abstract class BaseMVVMActivity<VM: BaseViewModel<ViewState>, ViewState>(val clazz: KClass<VM>): AppCompatActivity() {

    // Lazy Inject ViewModel
    private lateinit var viewModel: VM
    //
    private lateinit var lifeCycleRegistry : LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewId())
        viewModel = initViewModel()
        //
        lifeCycleRegistry = LifecycleRegistry(this)
        // Set lifecycle aware view model
        // lifecycle.addObserver(viewModel)
        // Custom life cycle observer
        lifeCycleRegistry.addObserver(viewModel)
        lifeCycleRegistry.markState(Lifecycle.State.INITIALIZED)
        getViewModel().onLifeCycleInitialized()
        onActivityCreated(savedInstanceState)
        getViewModel().getViewState().observe(this, Observer { onViewStateChanged(it) })
    }

    protected open fun initViewModel(): VM {
        // getViewModel(clazz = clazz) { parametersOf(viewModelParams) }
        return getViewModel(clazz = clazz)
    }

    abstract fun getViewId(): Int

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getViewModel(): VM {
        return viewModel
    }

    @Suppress("MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")
    protected open fun onViewStateChanged(state: ViewState?) {}

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