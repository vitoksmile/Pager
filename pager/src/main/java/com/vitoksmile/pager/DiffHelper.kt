package com.vitoksmile.pager

import androidx.recyclerview.widget.DiffUtil

object DiffHelper {

    fun <T> computeDiff(
        oldList: PagedList<T>,
        newList: PagedList<T>,
        diffCallback: DiffUtil.ItemCallback<T>
    ): DiffUtil.DiffResult {
        val oldSize = oldList.size
        val newSize = newList.size

        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldItem = oldList[oldItemPosition]
                val newItem = newList[newItemPosition]
                return diffCallback.getChangePayload(oldItem, newItem)
            }

            override fun getOldListSize() = oldSize

            override fun getNewListSize() = newSize

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldList[oldItemPosition]
                val newItem = newList[newItemPosition]
                if (oldItem === newItem) {
                    return true
                }
                return diffCallback.areItemsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldList[oldItemPosition]
                val newItem = newList[newItemPosition]
                if (oldItem === newItem) {
                    return true
                }
                return diffCallback.areContentsTheSame(oldItem, newItem)

            }
        }, true)
    }
}