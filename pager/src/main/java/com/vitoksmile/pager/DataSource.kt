package com.vitoksmile.pager

abstract class DataSource<T> {

    var loadCallback: DataSourceLoadCallback<T>? = null

    /**
     * Called when the DataSource will be in active state
     */
    open suspend fun onActive() {}

    /**
     * Called when need to load the new [page]
     * Loaded data should be passed to the [callback]
     */
    abstract suspend fun load(page: Int, callback: DataSourceLoadCallback<T>)

    /**
     * Called when user watch to fetch all data again
     *
     * Example, you need to clear your DB here
     */
    open suspend fun fetch() {}

    /**
     * Call the method to notify that the new [data] were uploaded
     */
    protected fun onUpdated(data: List<T>) {
        loadCallback?.onLoaded(data)
    }

    /**
     * Called when the DataSource will be in not active state
     */
    open fun onInactive() {}
}