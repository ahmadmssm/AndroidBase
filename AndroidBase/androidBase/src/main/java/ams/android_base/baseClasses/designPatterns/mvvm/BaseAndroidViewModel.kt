package ams.android_base.baseClasses.designPatterns.mvvm

import ams.android_base.utils.liveDataUtils.SingleLiveEvent
import android.util.Log
import androidx.lifecycle.*

@Suppress("unused")
open class BaseAndroidViewModel<ViewState>: ViewModel() {

    @Suppress("PrivatePropertyName")
    private val VIEW_MODEL_TAG = this.javaClass.simpleName.toString()
    private val mViewState = SingleLiveEvent<ViewState>()


    protected fun updateViewState(viewState: ViewState) {
        mViewState.value = viewState
    }

    open fun getViewState(): LiveData<ViewState> {
        return mViewState
    }

    //
    open fun onLifeCycleInitialized() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onLifeCycleChangeListener(owner: LifecycleOwner, event: Lifecycle.Event) {
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