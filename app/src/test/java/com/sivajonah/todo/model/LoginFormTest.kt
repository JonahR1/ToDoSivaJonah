package com.sivajonah.todo.model

import com.sivajonah.todo.model.LoginForm
import org.junit.Test
import org.junit.Assert.*

class LoginFormTest {
    private val email = "test@test.fr"
    private val password = "test"
    private val loginForm = LoginForm(email, password)

    @Test
    fun getEmail() {
        assertEquals(email, loginForm.email)
    }

    @Test
    fun getPassword() {
        assertEquals(password, loginForm.password)
    }
}