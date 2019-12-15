package com.robusta.bootstrap.pagination.localAndRemoteFetcher

import android.util.Log
import androidx.paging.PagedList
import com.ams.androiddevkit.baseClasses.pagination.localAndRemoteFetcher.PagingRequestHelper
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executors

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BasePagedListBoundaryCallBack<T>: PagedList.BoundaryCallback<T>() {

    protected val disposables: CompositeDisposable by lazy { CompositeDisposable() }
    @Suppress("HasPlatformType")
    protected val executor = Executors.newSingleThreadExecutor()
    protected val pagingRequestHelper = PagingRequestHelper(executor)
    protected var lastRequestedPage = 1

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        executeFetching(PagingRequestHelper.RequestType.INITIAL)
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        super.onItemAtEndLoaded(itemAtEnd)
        executeFetching(PagingRequestHelper.RequestType.AFTER)
    }

    private fun executeFetching(requestMode: PagingRequestHelper.RequestType) {
        pagingRequestHelper.runIfNotRunning(requestMode) { pagingRequestHelperCallBack ->
            disposables.add(fetchItemsForPage(lastRequestedPage)
                    .flatMapCompletable {
                        insertItemsIntoDatabase(it)
                    }
                    .subscribe({ executor.execute {
                        lastRequestedPage++
                        pagingRequestHelperCallBack.recordSuccess()
                    } },
                            { error ->
                                Log.d("executeFetching error",  error.stackTrace.toString())
                                pagingRequestHelperCallBack.recordFailure(error)
                            })
            )
        }
    }

    fun cleanUpDisposables() {
        disposables.dispose()
    }

    protected abstract fun fetchItemsForPage(pageNumber: Int): Single<List<T>>

    protected abstract fun insertItemsIntoDatabase(reposList: List<T>): Completable
}