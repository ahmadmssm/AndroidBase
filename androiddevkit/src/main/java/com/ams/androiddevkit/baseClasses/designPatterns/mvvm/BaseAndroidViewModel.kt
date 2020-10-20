package com.ams.androiddevkit.baseClasses.designPatterns.mvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ams.androiddevkit.utils.liveDataUtils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

@Suppress("unused")
open class BaseAndroidViewModel<ViewState>(application: Application): AndroidViewModel(application), LifecycleObserver, KoinComponent {

    @Suppress("PrivatePropertyName")
    private val VIEW_MODEL_TAG = this.javaClass.simpleName
    @Suppress("MemberVisibilityCanBePrivate")
    protected val viewState = SingleLiveEvent<ViewState>()

    protected open fun postViewState(state: ViewState) {
        // Ensures that the state will be posted on UI Thread
        GlobalScope.launch(Dispatchers.Main) {
            viewState.value = state
        }
    }

    open fun getViewState(): LiveData<ViewState> = viewState

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onLifeCycleChangeListener(owner: LifecycleOwner, event: Lifecycle.Event) {}

    protected open fun onLifeCycleInitialized() {
        Log.d(VIEW_MODEL_TAG, "view model onLifeCycleInitialized called")
    }

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