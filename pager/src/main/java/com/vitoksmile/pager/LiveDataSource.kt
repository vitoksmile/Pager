package com.vitoksmile.pager

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * DataSource to observe the [liveData] and notify callback about it updates
 */
abstract class LiveDataSource<T>(
    private val liveData: LiveData<List<T>>
) : DataSource<T>() {

    private val observer = Observer<List<T>> {
        onUpdated(it)
    }

    private var isInitialized = false

    open suspend fun init() {}

    override suspend fun onActive() {
        if (!isInitialized) {
            isInitialized = true
            init()
        }

        withContext(Dispatchers.Main) {
            liveData.observeForever(observer)
        }
    }

    override fun onInactive() {
        liveData.removeObserver(observer)
    }
}