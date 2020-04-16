package com.example.simpleworker

import android.content.Context
import android.os.Handler
import android.os.HandlerThread

class SimpleWorker(context: Context, tag: String) : HandlerThread(tag) {
    private var handler: Handler? = null
    private val uiHandler = Handler(context.mainLooper)

    init {
        start()
    }

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = Handler(looper)
    }

    fun <T> addTask(task: Task<T>) {
        handler?.post {
            val onExecuteTask = task.onExecuteTask()
            uiHandler.post { task.onCompleteTask(onExecuteTask) }
        }
    }
}