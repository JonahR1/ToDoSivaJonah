package com.sivajonah.todo.tasklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sivajonah.todo.network.TasksRepository
import com.sivajonah.todo.network.TasksRepositoryInterface
import kotlinx.coroutines.launch

class TaskListViewModel(private val tasksRepository: TasksRepositoryInterface = TasksRepository()): ViewModel() {
    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    fun refresh() {
        viewModelScope.launch {
            println("kekou ?")
            _taskList.value = tasksRepository.refresh()
        }
    }

    fun updateTask(task:Task) {
        viewModelScope.launch {
            tasksRepository.updateTask(task)?.let { task ->
                val editableList = _taskList.value.orEmpty().toMutableList()
                val position = editableList.indexOfFirst { task.id == it.id }
                editableList[position] = task
                _taskList.value = editableList
            }
        }
    }

    fun createTask(task:Task) {
        viewModelScope.launch {
            tasksRepository.createTask(task)?.let { task ->
                val editableList = _taskList.value.orEmpty().toMutableList()
                editableList.add(task)
                _taskList.value = editableList
            }
        }
    }

    fun deleteTask(task:Task) {
        viewModelScope.launch {
            if (tasksRepository.deleteTask(task)) {
                val editableList = _taskList.value.orEmpty().toMutableList()
                editableList.remove(task)
                _taskList.value = editableList
            }
        }
    }
}