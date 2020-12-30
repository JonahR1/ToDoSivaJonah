package com.sivajonah.todo.repository

import com.sivajonah.todo.model.Task

interface TasksRepositoryInterface {
    suspend fun refresh(): List<Task>?

    suspend fun createTask(task: Task) : Task?

    suspend fun deleteTask(task: Task) : Boolean

    suspend fun updateTask(task: Task): Task?
}