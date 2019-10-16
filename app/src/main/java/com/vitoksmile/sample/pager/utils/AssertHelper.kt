package com.vitoksmile.sample.pager.utils

import com.vitoksmile.sample.pager.PagerApplication.Companion.appContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader

class AssertHelper {

    suspend fun load(name: String): List<String> = withContext(Dispatchers.IO) {
        appContext().assets.open(name)
            .bufferedReader()
            .use(BufferedReader::readText)
            .lines()
    }
}