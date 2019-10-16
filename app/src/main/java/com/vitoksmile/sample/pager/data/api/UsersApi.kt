package com.vitoksmile.sample.pager.data.api

import com.vitoksmile.sample.pager.data.models.User
import com.vitoksmile.sample.pager.utils.AssertHelper
import kotlinx.coroutines.delay

class UsersApi {

    private companion object {

        const val USERS_PER_PAGE = 10
        const val MAX_PAGES = 10
    }

    private val assertHelper = AssertHelper()
    private lateinit var firstNames: List<String>
    private lateinit var lastNames: List<String>
    private lateinit var emails: List<String>

    suspend fun get(page: Int): Response {
        val users = fakeUsers(
            page,
            USERS_PER_PAGE
        )
        val isLastPage = page == MAX_PAGES
        delay(500)
        return Response(users, isLastPage)
    }

    private suspend fun fakeUsers(page: Int, count: Int) = arrayListOf<User>().apply {
        if (!::firstNames.isInitialized) {
            firstNames = assertHelper.load("first_names.txt")
        }
        if (!::lastNames.isInitialized) {
            lastNames = assertHelper.load("last_names.txt")
        }
        if (!::emails.isInitialized) {
            emails = assertHelper.load("emails.txt")
        }

        for (i in 0 until count) {
            add(fakeUser(page * count + i))
        }
    }

    private fun fakeUser(id: Int) =
        User(fakeFirstName(), fakeLastName(), fakeEmail(), fakeAvatar(id))

    private fun fakeFirstName() = firstNames.random()

    private fun fakeLastName() = lastNames.random()

    private fun fakeEmail() = emails.random()

    private fun fakeAvatar(id: Int) = "https://picsum.photos/id/$id/300/300"

    class Response(
        val users: List<User>,
        val isLastPage: Boolean
    )
}