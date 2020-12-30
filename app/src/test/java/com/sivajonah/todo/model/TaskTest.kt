package com.sivajonah.todo.model

import com.sivajonah.todo.model.Task
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TaskTest {
    private val id = "1"
    private val title = "TitleTest"
    private val description = "Test description"
    private val task = Task(id = id, title = title, description = description)

    @Test
    fun testGetId() {
        assertEquals(id, task.id)
    }

    @Test
    fun testGetTitle() {
        assertEquals(title, task.title)
    }

    @Test
    fun testGetDescription() {
        assertEquals(description, task.description)
    }
}