package com.sivajonah.todo.data.source

import com.sivajonah.todo.network.TasksWebService
import com.sivajonah.todo.network.UserWebService
import com.sivajonah.todo.tasklist.Task
import retrofit2.Response
import retrofit2.Response.success

class FakeTasksDataSource (var tasks: MutableList<Task>? = mutableListOf()): TasksWebService {
    override suspend fun getTasks(): Response<List<Task>> {
        tasks?.let { return success(ArrayList(it)) }
        return error(
            Exception("Tasks not found")
        )
    }

    override suspend fun deleteTask(id: String?): Response<String> {
        tasks?.removeIf { it -> it.id == id }.let { return success("204") }
        return error(
            Exception("Fail to delete task")
        )
    }

    override suspend fun createTask(task: Task): Response<Task> {
        tasks?.add(task).let { return success(task) }
        return error(
            Exception("Fail to add task")
        )
    }

    override suspend fun updateTask(task: Task, id: String?): Response<Task> {
        tasks?.indexOfFirst { it -> it.id == id }?.let {
            tasks?.set(it, task)?.let {
                return success(task)
            }
        }
        return error(
            Exception("Fail to update task")
        )
    }
}