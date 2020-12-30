package com.sivajonah.todo.repository

import com.sivajonah.todo.data.source.FakeTasksDataSource
import com.sivajonah.todo.model.Task
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TasksRepositoryTest : TestCase() {
    private val task1 = Task("1", "1st Task", "Do 1st Task")
    private val task2 = Task("2", "2nd Task", "Do 2nd Task")
    private val task3 = Task("3", "3rd Task", "Do 3rd Task")
    private val task4 = Task("4", "4th Task", "Do 4th Task")
    private val tasks = listOf(task1, task2, task3)

    private var tasksTasksDataSource: FakeTasksDataSource = FakeTasksDataSource(tasks.toMutableList())
    private var tasksRepository: TasksRepository = TasksRepository(tasksTasksDataSource)

    fun test_1_Refresh() = runBlockingTest {
        val tasksResult = tasksRepository.refresh()

        assertEquals(tasks, tasksResult)
    }

    fun test_2_CreateTask() = runBlockingTest {
        tasksRepository.createTask(task4)

        assertEquals(listOf(task1, task2, task3, task4), tasksTasksDataSource.tasks?.toList())
    }

    fun test_3_DeleteTask() = runBlockingTest {
        tasksRepository.deleteTask(task1)

        assertEquals(listOf(task2, task3), tasksTasksDataSource.tasks?.toList())
    }

    fun test_4_UpdateTask() = runBlockingTest  {
        task1.title = task4.title
        task1.description = task4.description
        tasksRepository.updateTask(task1)

        assertEquals(listOf(task1, task2, task3), tasksTasksDataSource.tasks?.toList())
    }
}