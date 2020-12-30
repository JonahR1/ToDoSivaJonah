package com.sivajonah.todo.model

import com.sivajonah.todo.model.LoginResponse
import org.junit.Test

import org.junit.Assert.*

class LoginResponseTest {
    private val token = "fakeToken"

    private val loginResponse = LoginResponse(token)

    @Test
    fun getToken() {
        assertEquals(token, loginResponse.token)
    }
}