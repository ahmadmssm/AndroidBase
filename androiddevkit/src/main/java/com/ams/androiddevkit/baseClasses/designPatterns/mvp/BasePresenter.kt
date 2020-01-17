package com.ams.androiddevkit.baseClasses.designPatterns.mvp

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@Suppress("unused")
open class BasePresenter<ViewDelegate: BaseView>(private var viewDelegate: ViewDelegate?): LifecycleObserver {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val disposeBag: CompositeDisposable by lazy { CompositeDisposable() }

    protected open fun addToDisposeBag(disposable: Disposable) {
        disposeBag.add(disposable)
    }

    protected open fun logout() {}

    open fun onLifeCycleInitialized() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onLifeCycleChangeListener(owner: LifecycleOwner, event: Lifecycle.Event) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onViewCreated(owner: LifecycleOwner) {}

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart(owner: LifecycleOwner) {}

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume(owner: LifecycleOwner) {}

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause(owner: LifecycleOwner) {}

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop(owner: LifecycleOwner) {}

    // Kept just for unit testing only
    protected open fun onStop() {}

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    protected open fun onDestroy(owner: LifecycleOwner) {
        if(!disposeBag.isDisposed) disposeBag.dispose()
        this.viewDelegate = null
    }
}