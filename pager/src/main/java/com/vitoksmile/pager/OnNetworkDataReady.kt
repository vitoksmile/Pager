package com.vitoksmile.pager

import androidx.lifecycle.LiveData

typealias OnNetworkDataReady = (networkData: LiveData<NetworkState>) -> Unit