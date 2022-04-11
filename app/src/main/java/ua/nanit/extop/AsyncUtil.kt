package ua.nanit.extop

import java.util.concurrent.Executor
import java.util.concurrent.Executors

object AsyncUtil {

    private lateinit var executor: Executor

    fun init() {
        executor = Executors.newSingleThreadExecutor()
    }

    fun executor(): Executor = executor

}