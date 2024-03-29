package com.vitoksmile.sample.pager.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vitoksmile.pager.NetworkState
import com.vitoksmile.sample.pager.domain.UsersInteractor

class UsersViewModel : ViewModel() {

    private val interactor = UsersInteractor()

    lateinit var networkData: LiveData<NetworkState>
        private set
    val usersData = interactor.getUsersData { networkData = it }
}