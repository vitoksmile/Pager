package com.vitoksmile.pager

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Pager<T>(
    scope: CoroutineScope,
    private val config: PagerConfig,
    private val dataSource: DataSource<T>,
    private val loadCallback: LoadCallback<T>,
    private val networkData: MutableLiveData<NetworkState>
) : CoroutineScope by scope {

    companion object {

        const val PAGE_DEFAULT = 0
    }

    /**
     * Hold the all data to support prefetch new items
     */
    private val onPrefetchListener = { index: Int -> preFetch(index) }
    private val pagedList =
        PrefetchPagedList<T>(onPreFetchListener = onPrefetchListener, fetchListener = { fetch() })

    private var page: Int? = PAGE_DEFAULT
    private var currentPage: Int? = null
    private var totalSize = 0

    init {
        /**
         * Register callback to get notifications when DataSource loaded data.
         * The data will be send to loadCallback
         */
        dataSource.loadCallback = object : DataSourceLoadCallback<T> {
            override fun onLoaded(data: List<T>) {
                pagedList.replaceAll(data)
                totalSize = pagedList.size
                loadCallback.onLoaded(pagedList.copy())
            }
        }

        load()
    }

    private fun load() = launch(Dispatchers.IO) {
        val page = page ?: return@launch
        // Disable load the current page again
        if (page == currentPage) return@launch
        currentPage = page

        withContext(Dispatchers.Main) {
            // Show loading
            networkData.value = NetworkState.loading()
        }

        val callback = object : DataSourceLoadCallback<T> {
            override fun onLoaded(data: List<T>, isLastPage: Boolean) {
                // Update the next page
                this@Pager.page = if (isLastPage) null else page + 1

                // Hide loading
                networkData.postValue(NetworkState.success())

                pagedList.addAll(data)
                totalSize = pagedList.size
                loadCallback.onLoaded(pagedList.copy())
            }
        }

        try {
            // Load new data
            dataSource.load(page, callback)
        } catch (error: Throwable) {
            error.printStackTrace()

            withContext(Dispatchers.Main) {
                // Show error
                networkData.value = NetworkState.error(error)
            }
        }
    }

    /**
     * Prefetch new items based on the [index]
     */
    private fun preFetch(index: Int) {
        val indexToPrefetch = totalSize - config.prefetchDistance - 1
        if (index >= indexToPrefetch) load()
    }

    /**
     * Load all data from the first page
     */
    private fun fetch() {
        launch(Dispatchers.IO) {
            dataSource.fetch()
            page = 0
            currentPage = null
            load()
        }
    }
}