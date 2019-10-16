package com.vitoksmile.pager.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vitoksmile.pager.DiffHelper
import com.vitoksmile.pager.PagedList
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class AnimatedPagedAdapter<T, VH : RecyclerView.ViewHolder>(
    private val diffCallback: DiffUtil.ItemCallback<T>
) : BasePagedAdapter<T, VH>(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job + CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    private var computeJob: Job? = null

    override fun setData(data: PagedList<T>) {
        computeJob?.cancel()
        computeJob = launch {
            val result = DiffHelper.computeDiff(pagedList, data, diffCallback)

            withContext(Dispatchers.Main) {
                onDataReady(data)
                result.dispatchUpdatesTo(this@AnimatedPagedAdapter)
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        job.cancelChildren()
    }
}