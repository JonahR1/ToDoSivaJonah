package com.sivajonah.todo.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sivajonah.todo.data.source.FakeTasksRepository
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TaskListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private val task1 = Task("1", "1st Task", "Do 1st Task")
    private val task2 = Task("2", "2nd Task", "Do 2nd Task")
    private val task3 = Task("3", "3rd Task", "Do 3rd Task")
    private val task4 = Task("4", "4th Task", "Do 4th Task")

    private lateinit var tasksRepository : FakeTasksRepository
    private lateinit var tasksViewModel : TaskListViewModel

    @Before
    fun setup() {
        // Set Coroutine Dispatcher.
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun finish() {
        Dispatchers.resetMain()
        // Reset Coroutine Dispatcher and Scope.
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun testRefresh() = testDispatcher.runBlockingTest {
        tasksRepository = FakeTasksRepository()
        tasksRepository.addTasks(task1, task2, task3)
        tasksViewModel = TaskListViewModel(tasksRepository)

        tasksViewModel.refresh()

        assertEquals(listOf(task1, task2, task3), tasksViewModel.taskList.value)
    }

    fun testUpdateTask() {}

    fun testCreateTask() {}

    fun testDeleteTask() {}
}