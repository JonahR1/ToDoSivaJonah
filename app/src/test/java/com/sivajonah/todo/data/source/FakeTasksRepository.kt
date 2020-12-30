package com.sivajonah.todo.data.source

import androidx.lifecycle.MutableLiveData
import com.sivajonah.todo.network.TasksRepositoryInterface
import com.sivajonah.todo.tasklist.Task

class FakeTasksRepository : TasksRepositoryInterface {
    var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap()

    private val observableTasks = MutableLiveData<Result<List<Task>>>()

    override suspend fun refresh(): List<Task>? {
        return tasksServiceData.values.toList()
    }

    override suspend fun createTask(task: Task): Task? {
        tasksServiceData[task.id] = task
        return tasksServiceData[task.id]
    }

    override suspend fun deleteTask(task: Task): Boolean {
        tasksServiceData.remove(task.id)?.let {
            return true
        }
        return false
    }

    override suspend fun updateTask(task: Task): Task? {
        tasksServiceData[task.id] = task
        return tasksServiceData[task.id]
    }

    fun addTasks(task1: Task, task2: Task, task3: Task): FakeTasksRepository {
        tasksServiceData[task1.id] = task1
        tasksServiceData[task2.id] = task2
        tasksServiceData[task3.id] = task3

        return this
    }
}