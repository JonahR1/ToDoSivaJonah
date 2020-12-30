package com.sivajonah.todo.tasklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sivajonah.todo.data.source.FakeTasksRepository
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runners.MethodSorters


@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TaskListViewModelTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private val task1 = Task("1", "1st Task", "Do 1st Task")
    private val task2 = Task("2", "2nd Task", "Do 2nd Task")
    private val task3 = Task("3", "3rd Task", "Do 3rd Task")
    private val task4 = Task("4", "4th Task", "Do 4th Task")

    private var tasksRepository : FakeTasksRepository = FakeTasksRepository()
    private var tasksViewModel : TaskListViewModel = TaskListViewModel(tasksRepository.addTasks(task1, task2, task3))

    @Before
    fun refresh() {
        tasksViewModel.refresh()
    }

    companion object {
        private val testDispatcher = TestCoroutineDispatcher()
        private val testScope = TestCoroutineScope(testDispatcher)

        @BeforeClass
        @JvmStatic
        fun setup() {
            /* Set Coroutine Dispatcher. */
            Dispatchers.setMain(testDispatcher)
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            Dispatchers.resetMain()
            // Reset Coroutine Dispatcher and Scope.
            testDispatcher.cleanupTestCoroutines()
            testScope.cleanupTestCoroutines()
        }
    }

    @Test
    fun test_1_Refresh() = runBlockingTest {
        assertEquals(listOf(task1, task2, task3), tasksViewModel.taskList.value)
    }

    @Test
    fun test_2_UpdateTask() = runBlockingTest {
        task1.title = "IMPORTANT"
        tasksViewModel.updateTask(task1)

        assertEquals(listOf(task1, task2, task3), tasksViewModel.taskList.value)
    }

    @Test
    fun test_3_CreateTask() {
        tasksViewModel.createTask(task4)

        assertEquals(listOf(task1, task2, task3, task4), tasksViewModel.taskList.value)
    }

    @Test
    fun test_4_DeleteTask() {
        tasksViewModel.deleteTask(task1)

        assertEquals(listOf(task2, task3), tasksViewModel.taskList.value)
    }
}