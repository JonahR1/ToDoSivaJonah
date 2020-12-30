package com.sivajonah.todo.tasklist

import junit.framework.TestCase

class TaskTest : TestCase() {
    private val id = "1"
    private val title = "TitleTest"
    private val description = "Test description"
    private val task = Task(id = id, title = title, description = description)

    fun testConstructor() {
        val task = Task(id = id, title = title, description = description)

        assertEquals(id, task.id)
        assertEquals(title, task.title)
        assertEquals(description, task.description)
    }

    fun testGetId() {
        assertEquals(id, task.id)
    }

    fun testGetTitle() {
        assertEquals(title, task.title)
    }

    fun testGetDescription() {
        assertEquals(description, task.description)
    }

    fun testCopy() {
        val newTask = task.copy()
        assert(newTask == task)
    }
}