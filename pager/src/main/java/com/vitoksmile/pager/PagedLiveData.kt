package com.vitoksmile.pager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * [DataSource] will be converted to the LiveData.
 * When [Pager] loaded new data, the data will be sent observers.
 */
class PagedLiveData<T>(
    config: PagerConfig,
    private val dataSource: DataSource<T>,
    onNetworkDataReady: OnNetworkDataReady
) : LiveData<PagedList<T>>(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    private val loadCallback = object : LoadCallback<T> {
        override fun onLoaded(data: PagedList<T>) {
            setData(data)
        }
    }

    private val networkData = MutableLiveData<NetworkState>()

    init {
        onNetworkDataReady(networkData)
        Pager(this, config, dataSource, loadCallback, networkData)
    }

    private fun setData(data: PagedList<T>) = launch {
        value = data
    }

    override fun onActive() {
        launch(Dispatchers.IO) {
            dataSource.onActive()
        }
    }

    override fun onInactive() {
        dataSource.onInactive()
        job.cancelChildren()
    }
}

fun <T> DataSource<T>.toLiveData(
    config: PagerConfig,
    onNetworkDataReady: OnNetworkDataReady
): LiveData<PagedList<T>> =
    PagedLiveData(config, this, onNetworkDataReady)