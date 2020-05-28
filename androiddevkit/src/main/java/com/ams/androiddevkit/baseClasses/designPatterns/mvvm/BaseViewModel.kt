package com.ams.androiddevkit.baseClasses.designPatterns.mvvm

import android.util.Log
import androidx.lifecycle.*
import com.ams.androiddevkit.utils.liveDataUtils.LiveDataEvent
import org.koin.core.KoinComponent

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class BaseViewModel<ViewState>: ViewModel(), LifecycleObserver, KoinComponent {

    @Suppress("PrivatePropertyName")
    private val VIEW_MODEL_TAG = this.javaClass.simpleName
    protected val viewState = MutableLiveData<LiveDataEvent<ViewState>>()

    protected fun postViewState(state: ViewState) {
        viewState.value = LiveDataEvent(state)
    }

    open fun getViewState(): LiveData<LiveDataEvent<ViewState>> {
        return viewState
    }

    open fun onLifeCycleInitialized() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onLifeCycleChangeListener(owner: LifecycleOwner, event: Lifecycle.Event) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onViewCreated(owner: LifecycleOwner) {
        Log.d(VIEW_MODEL_TAG, "view model onViewCreated called")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart(owner: LifecycleOwner) {
        Log.d(VIEW_MODEL_TAG, "view model onStart called")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume(owner: LifecycleOwner) {
        Log.d(VIEW_MODEL_TAG, "view model onResume called")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause(owner: LifecycleOwner) {
        Log.d(VIEW_MODEL_TAG, "view model onPause called")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop(owner: LifecycleOwner) {
        Log.d(VIEW_MODEL_TAG, "view model onStop called")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy(owner: LifecycleOwner) {
        Log.d(VIEW_MODEL_TAG, "view model onDestroy called")
    }
}