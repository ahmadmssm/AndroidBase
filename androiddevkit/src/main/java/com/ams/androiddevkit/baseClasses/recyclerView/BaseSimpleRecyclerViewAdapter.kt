package com.ams.androiddevkit.baseClasses.recyclerView

import android.view.ViewGroup
import com.ams.androiddevkit.baseClasses.recyclerView.viewholder.BaseSimpleViewHolder

abstract class BaseSimpleRecyclerViewAdapter<T, VH: BaseSimpleViewHolder<T>>(items: List<T>): BaseRecyclerViewAdapter<T, VH>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = getNewViewHolder(parent, viewType)

    protected abstract fun getNewViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), position)
    }
}