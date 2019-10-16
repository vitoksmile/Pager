package com.vitoksmile.pager.adapter

import androidx.recyclerview.widget.RecyclerView
import com.vitoksmile.pager.PagedList

abstract class BasePagedAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected var pagedList: PagedList<T> = PagedList()
        private set

    abstract fun setData(data: PagedList<T>)

    protected fun onDataReady(data: PagedList<T>) {
        pagedList = data
    }

    /**
     * Fetch all data from the first page
     */
    fun fetch() {
        pagedList.fetch()
    }

    protected fun getItem(position: Int): T {
        pagedList.prefetch(position)
        return pagedList[position]
    }

    override fun getItemCount() = pagedList.size
}