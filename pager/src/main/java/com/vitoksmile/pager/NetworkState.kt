package com.vitoksmile.pager

class NetworkState private constructor(
    private val state: Int,
    val error: Throwable? = null
) {

    companion object {

        private const val STATE_LOADING = 0
        private const val STATE_SUCCESS = 1
        private const val STATE_ERROR = 2

        fun success() = NetworkState(STATE_SUCCESS)

        fun loading() = NetworkState(STATE_LOADING)

        fun error(error: Throwable) = NetworkState(STATE_ERROR, error)
    }

    val isLoading
        get() = state == STATE_LOADING

    override fun toString(): String {
        return when (state) {
            STATE_LOADING -> "NetworkState=loading"
            STATE_SUCCESS -> "NetworkState=success"
            STATE_ERROR -> "NetworkState=error,$error"
            else -> super.toString()
        }
    }
}