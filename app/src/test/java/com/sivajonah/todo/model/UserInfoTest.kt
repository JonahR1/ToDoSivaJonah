package com.sivajonah.todo.model

import com.sivajonah.todo.model.UserInfo
import junit.framework.TestCase

class UserInfoTest : TestCase() {
    private val email = "test@test.com"
    private val firstName = "TEST"
    private val lastName = "test"
    private val avatar = "AVATAR"
    private val userInfo = UserInfo(email = email, firstName = firstName, lastName = lastName, avatar = avatar)

    fun testGetEmail() {
        assertEquals(email, userInfo.email)
    }

    fun testGetFirstName() {
        assertEquals(firstName, userInfo.firstName)
    }

    fun testGetLastName() {
        assertEquals(lastName, userInfo.lastName)
    }

    fun testGetAvatar() {
        assertEquals(avatar, userInfo.avatar)
    }
}