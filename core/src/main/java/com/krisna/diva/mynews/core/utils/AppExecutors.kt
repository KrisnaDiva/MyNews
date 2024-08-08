package com.krisna.diva.mynews.core.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(
    private val diskIO: Executor = Executors.newSingleThreadExecutor()
) {
    fun diskIO(): Executor = diskIO
}
