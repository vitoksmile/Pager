package com.vitoksmile.pager

open class PagedList<T> : AbstractList<T>() {

    protected val data = mutableListOf<T>()

    override fun get(index: Int) = data[index]

    override val size: Int
        get() = data.size

    open fun prefetch(index: Int) {}

    /**
     * Fetch all data from the first page
     */
    open fun fetch() {}
}

/**
 * [PagedList] to support mutable data
 */
open class MutablePagedList<T> : PagedList<T>() {

    fun addAll(data: List<T>) {
        this.data.addAll(data)
    }

    fun replaceAll(data: List<T>) {
        this.data.clear()
        addAll(data)
    }
}

/**
 * [PagedList] to support callback to when need to prefetch
 */
class PrefetchPagedList<T>(
    private val onPreFetchListener: (index: Int) -> Unit,
    private val fetchListener: () -> Unit
) : MutablePagedList<T>() {

    override fun prefetch(index: Int) {
        onPreFetchListener(index)
    }

    override fun fetch() {
        fetchListener()
    }

    fun copy(pagedList: PrefetchPagedList<T> = this) =
        PrefetchPagedList<T>(pagedList.onPreFetchListener, pagedList.fetchListener).apply {
            replaceAll(pagedList)
        }
}