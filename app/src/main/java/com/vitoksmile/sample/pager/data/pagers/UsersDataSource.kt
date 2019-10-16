package com.vitoksmile.sample.pager.data.pagers

import com.vitoksmile.pager.DataSource
import com.vitoksmile.pager.DataSourceLoadCallback
import com.vitoksmile.sample.pager.data.api.UsersApi
import com.vitoksmile.sample.pager.data.models.User

class UsersDataSource : DataSource<User>() {

    private val api = UsersApi()

    override suspend fun load(page: Int, callback: DataSourceLoadCallback<User>) {
        val response = api.get(page)
        callback.onLoaded(response.users, response.isLastPage)
    }
}