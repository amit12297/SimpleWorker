package com.example.simpleworker

interface Task<T> {
    fun onExecuteTask(): T
    fun onCompleteTask(result: T)
}