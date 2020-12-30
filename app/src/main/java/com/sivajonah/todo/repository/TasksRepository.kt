package com.sivajonah.todo.repository

import com.sivajonah.todo.model.Task
import com.sivajonah.todo.network.Api
import com.sivajonah.todo.webservice.TasksWebService

class TasksRepository (private val tasksWebService: TasksWebService = Api.INSTANCE.tasksWebService) :
    TasksRepositoryInterface {
    override suspend fun refresh(): List<Task>? {
        val response = tasksWebService.getTasks()
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun createTask(task: Task) : Task? {
        val response = tasksWebService.createTask(task)
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun deleteTask(task: Task) : Boolean {
        return task.id != null && tasksWebService.deleteTask(task.id).isSuccessful
    }

    override suspend fun updateTask(task: Task): Task? {
        val response = tasksWebService.updateTask(task)
        return if (response.isSuccessful) response.body() else null
    }
}