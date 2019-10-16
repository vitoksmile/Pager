package com.vitoksmile.sample.pager

import android.app.Application

class PagerApplication : Application() {

    companion object {

        lateinit var instance: Application
            private set

        fun appContext(): Application = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}