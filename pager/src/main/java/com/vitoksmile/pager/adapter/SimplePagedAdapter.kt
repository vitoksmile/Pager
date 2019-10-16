package com.vitoksmile.pager.adapter

import androidx.recyclerview.widget.RecyclerView
import com.vitoksmile.pager.PagedList

abstract class SimplePagedAdapter<T, VH : RecyclerView.ViewHolder> : BasePagedAdapter<T, VH>() {

    override fun setData(data: PagedList<T>) {
        onDataReady(data)
        notifyDataSetChanged()
    }
}