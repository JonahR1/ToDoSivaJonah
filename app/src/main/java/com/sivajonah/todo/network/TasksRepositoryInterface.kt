package com.sivajonah.todo.network

import com.sivajonah.todo.tasklist.Task

interface TasksRepositoryInterface {
    suspend fun refresh(): List<Task>?

    suspend fun createTask(task: Task) : Task?

    suspend fun deleteTask(task: Task) : Boolean

    suspend fun updateTask(task: Task): Task?
}