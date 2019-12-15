package com.robusta.bootstrap.pagination.remoteFetcher

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.robusta.bootstrap.pagination.PaginationState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseRemotePageKeyedDataSource<T>: PageKeyedDataSource<Int, T>() {

    val paginationState: MutableLiveData<PaginationState> = MutableLiveData()
    protected val disposables: CompositeDisposable by lazy { CompositeDisposable() }
    protected var currentPage = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        // Cannot invoke Live data setValue on a background thread, so i used Coroutine threading to set the Livedata on UI thread
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                paginationState.value = PaginationState.LoadInitial()
                paginationState.value = PaginationState.Loading()
            }
        }
        disposables.add(fetchItemsForPage(currentPage).observeOn(AndroidSchedulers.mainThread()).subscribe({ items ->
            paginationState.value = PaginationState.Loaded()
            currentPage++
            callback.onResult(items, null,  currentPage)
        }, { error ->
            Log.d("loadInitial error",  error.stackTrace.toString())
            paginationState.value = PaginationState.Error(error)
        }))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // Cannot invoke Live data setValue on a background thread, so i used Coroutine threading to set the Livedata on UI thread
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                paginationState.value = PaginationState.LoadBefore()
                paginationState.value = PaginationState.Loading()
            }
        }
        disposables.add(fetchItemsForPage(params.key).observeOn(AndroidSchedulers.mainThread()).subscribe({ repos ->
            paginationState.value = PaginationState.Loaded()
            val key = if (params.key > 1) params.key - 1 else 0
            currentPage = key
            callback.onResult(repos, key)
        }, { error ->
            Log.d("loadBefore error",  error.stackTrace.toString())
            paginationState.value = PaginationState.Error(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // Cannot invoke Live data setValue on a background thread, so i used Coroutine threading to set the Livedata on UI thread
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                paginationState.value = PaginationState.LoadAfter()
                paginationState.value = PaginationState.Loading()
            }
        }
        disposables.add(fetchItemsForPage(params.key).observeOn(AndroidSchedulers.mainThread()).subscribe({ repos ->
            paginationState.value = PaginationState.Loaded()
            val key = if (params.key > 1) params.key + 1 else 0
            currentPage = key
            callback.onResult(repos, key)
        }, { error ->
            Log.d("loadAfter error",  error.stackTrace.toString())
            paginationState.value = PaginationState.Error(error)
        }))
    }

    protected fun cleanUpDisposables() {
        disposables.dispose()
    }

    protected abstract fun fetchItemsForPage(pageNumber: Int): Single<List<T>>
}
