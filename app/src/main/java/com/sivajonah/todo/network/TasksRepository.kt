package com.sivajonah.todo.network

import com.sivajonah.todo.tasklist.Task

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    suspend fun refresh(): List<Task>? {
        val response = tasksWebService.getTasks()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createTask(task: Task) : Task? {
        val response = tasksWebService.createTask(task)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteTask(task: Task) : Boolean {
        return task.id != null && tasksWebService.deleteTask(task.id).isSuccessful
    }

    suspend fun updateTask(task: Task): Task? {
        val response = tasksWebService.updateTask(task)
        return if (response.isSuccessful) response.body() else null
    }
}