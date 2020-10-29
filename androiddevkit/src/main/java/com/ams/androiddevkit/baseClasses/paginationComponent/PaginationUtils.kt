package com.ams.androiddevkit.baseClasses.paginationComponent

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.recyclerview.widget.DiffUtil

@Suppress("unused")
open class PaginationUtils {

    fun <T>getPagedListBuilder(dataSourceFactory: DataSource.Factory<Int, T>,
                               boundaryCallBack: PagedList.BoundaryCallback<T>,
                               config: PagedList.Config = getDefaultPagedListConfigs()): LivePagedListBuilder<Int, T> {
        val livePagedListBuilder = LivePagedListBuilder(dataSourceFactory, config)
        livePagedListBuilder.setBoundaryCallback(boundaryCallBack)
        return livePagedListBuilder
    }

    fun <T>getRemotePagedListBuilder(dataSourceFactory: DataSource.Factory<Int, T>,
                                     config: PagedList.Config = getDefaultPagedListConfigs()): LivePagedListBuilder<Int, T> {
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun <T>getRxPagedListBuilder(dataSourceFactory: DataSource.Factory<Int, T>,
                                 boundaryCallBack: PagedList.BoundaryCallback<T>,
                                 config: PagedList.Config = getDefaultPagedListConfigs()): RxPagedListBuilder<Int, T> {
        val rxPagedListBuilder = RxPagedListBuilder(dataSourceFactory, config)
        rxPagedListBuilder.setBoundaryCallback(boundaryCallBack)
        return rxPagedListBuilder
    }

    fun <T>getRxRemotePagedListBuilder(dataSourceFactory: DataSource.Factory<Int, T>,
                                       config: PagedList.Config = getDefaultPagedListConfigs()): RxPagedListBuilder<Int, T> {
        return RxPagedListBuilder(dataSourceFactory, config)
    }

    fun <T: DiffUtilComparable>getDiffUtilComparator(): DiffUtil.ItemCallback<T> {
        return object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.getItemId() == newItem.getItemId()
            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = newItem == oldItem
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getDefaultPagedListConfigs(): PagedList.Config {
        return PagedList.Config.Builder()
                .setPrefetchDistance(10)
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .build()
    }
}