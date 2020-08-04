package com.ams.androiddevkit.baseClasses.designPatterns.mvvm

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseMVVMDialogFragment<VM: BaseViewModel<ViewState>, ViewState>: DialogFragment {

    private var viewModel: VM? = null
    protected var lifeCycleRegistry: LifecycleRegistry? = null
    protected lateinit var clazz: KClass<VM>

    constructor()

    constructor(@LayoutRes contentLayoutId: Int): super(contentLayoutId)

    constructor(clazz: KClass<VM>, @LayoutRes contentLayoutId: Int): super(contentLayoutId) {
        this.clazz = clazz
    }

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    // This function was overriden was mainly created to solve this exception"
    // java.lang.IllegalStateException: Fragment already added due to the Dialog fragments sometimes added twice quickly.
    // Ref: https://www.programmersought.com/article/9787616645/
    override fun show(manager: FragmentManager, tag: String?) {
        try {
            // Add a remove transaction before each add transaction to prevent continuous add
            manager.beginTransaction().remove(this).commit()
            super.show(manager, tag)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
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
            if (!it.getViewState().hasActiveObservers()) {
                it.getViewState().observe(viewLifecycleOwner, Observer { viewState ->
                    onViewStateChanged(viewState)
                })
            }
        }
    }

    protected open fun initViewModel(): VM? {
        if(::clazz.isInitialized) {
            // getViewModel(clazz = clazz) { parametersOf(viewModelParams) }
            return getViewModel(clazz = clazz)
        }
        return null
    }

    protected fun getViewModel(): VM? = viewModel

    protected abstract fun bindViews()

    protected abstract fun initUI()

    protected open fun initUI(bundle: Bundle?) {}

    protected abstract fun onViewStateChanged(state: ViewState)

    protected open fun onViewReady() {}

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
}