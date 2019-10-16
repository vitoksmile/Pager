package com.vitoksmile.sample.pager.domain

import com.vitoksmile.pager.OnNetworkDataReady
import com.vitoksmile.pager.PagerConfig
import com.vitoksmile.pager.toLiveData
import com.vitoksmile.sample.pager.data.pagers.UsersDataSource

class UsersInteractor {

    private val config = PagerConfig.Builder()
        .setPrefetchDistance(12)
        .build()

    fun getUsersData(onNetworkDataReady: OnNetworkDataReady) =
        UsersDataSource().toLiveData(config, onNetworkDataReady)
}