package com.vitoksmile.sample.pager.data.models

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String
) {

    val fullName
        get() = "$firstName $lastName"
}