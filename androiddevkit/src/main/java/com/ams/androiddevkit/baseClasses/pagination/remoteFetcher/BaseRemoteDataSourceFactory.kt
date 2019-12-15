package com.ams.androiddevkit.baseClasses.pagination.remoteFetcher

import androidx.paging.DataSource
import com.robusta.bootstrap.pagination.remoteFetcher.BaseRemotePageKeyedDataSource

open class BaseRemoteDataSourceFactory<T, PagedRemoteDataSource: BaseRemotePageKeyedDataSource<T>>(private val pagedRemoteDataSource: PagedRemoteDataSource): DataSource.Factory<Int, T>() {
    override fun create(): DataSource<Int, T> = pagedRemoteDataSource
}