package com.sivajonah.todo.model

import com.sivajonah.todo.model.SignUpForm
import org.junit.Test

import org.junit.Assert.*

class SignUpFormTest {
    private val firstname = "TEST"
    private val lastname = "test"
    private val email = "test@test.fr"
    private val password = "testpwd"
    private val password_confirmation = "testpwd"

    private val signupform = SignUpForm(firstname, lastname, email, password, password_confirmation)

    @Test
    fun getFirstname() {
        assertEquals(firstname, signupform.firstname)
    }

    @Test
    fun getLastname() {
        assertEquals(lastname, signupform.lastname)
    }

    @Test
    fun getEmail() {
        assertEquals(email, signupform.email)
    }

    @Test
    fun getPassword() {
        assertEquals(password, signupform.password)
    }

    @Test
    fun getPassword_confirmation() {
        assertEquals(password_confirmation, signupform.password_confirmation)
    }
}