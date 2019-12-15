package com.ams.androiddevkit.baseClasses.recyclerView

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtilCallback<T>(private var oldData: List<T>?, private var newData: List<T>?): DiffUtil.Callback() {

    fun setLists(oldData: List<T>?, newData: List<T>?) {
        this.oldData = oldData
        this.newData = newData
    }

    override fun getOldListSize(): Int {
        return oldData!!.size
    }

    override fun getNewListSize(): Int {
        return newData!!.size
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        //you can return particular field for changed item.
        return newData
        /*  T newItem = newData.get(newItemPosition);
        T oldItem = newData.get(oldItemPosition);
        Bundle diffBundle = new Bundle();
        if (newItem.getId() != oldItem.getId())
            diffBundle.putLong("id", newItem.getId());
        if (diffBundle.size() == 0) return null;
        return diffBundle;*/
    }
}
