package com.ams.androiddevkit.baseClasses.recyclerView

import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ams.androiddevkit.baseClasses.recyclerView.viewholder.BaseSimpleViewHolder

abstract class BasePagedListAdapter<T, VH: BaseSimpleViewHolder<T>>(@NonNull diffUtilCallback: DiffUtil.ItemCallback<T>): PagedListAdapter<T, VH>(diffUtilCallback) {
    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }
}