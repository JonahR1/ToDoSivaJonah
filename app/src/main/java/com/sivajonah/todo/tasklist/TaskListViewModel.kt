package com.sivajonah.todo.tasklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sivajonah.todo.network.TasksRepository
import kotlinx.coroutines.launch

class TaskListViewModel: ViewModel() {
    private val _taskList = MutableLiveData<List<Task>>()
    public val taskList: LiveData<List<Task>> = _taskList
    private val tasksRepository = TasksRepository()

    fun refresh() {
        viewModelScope.launch {
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