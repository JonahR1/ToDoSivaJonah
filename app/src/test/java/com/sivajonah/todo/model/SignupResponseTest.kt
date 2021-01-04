package com.sivajonah.todo.model

import org.junit.Test

import org.junit.Assert.*

class SignupResponseTest {
    private val token = "fakeToken"

    private val signupResponse = SignupResponse(token)

    @Test
    fun getToken() {
        assertEquals(token, signupResponse.token)
    }
}