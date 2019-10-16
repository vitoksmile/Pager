package com.vitoksmile.pager

interface LoadCallback<T> {

    fun onLoaded(data: PagedList<T>)
}

interface DataSourceLoadCallback<T> {

    fun onLoaded(data: List<T>) {}

    fun onLoaded(data: List<T>, isLastPage: Boolean) {}
}