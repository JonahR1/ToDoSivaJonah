package com.sivajonah.todo.repository

import com.sivajonah.todo.data.source.FakeUserWebService
import com.sivajonah.todo.model.UserInfo
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserInfoRepositoryTest {
    private val userInfo = UserInfo("test@test.fr", "TEST", "test", "test.png")

    private var userWebService: FakeUserWebService = FakeUserWebService(userInfo)
    private var userInfoRepository: UserInfoRepository = UserInfoRepository(userWebService)

    @Test
    fun test_1_GetInfo() = runBlockingTest {
        val result = userInfoRepository.getInfo()

        assertEquals(userInfo, result)
    }

    @Test
    fun test_2_UpdateAvatar() = runBlockingTest {
        val newAvatar =  MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "supertest.png",
            body = "hola".toRequestBody()
        )

        val result = userInfoRepository.updateAvatar(newAvatar)

        val newUserInfo = userInfo.copy()
        newUserInfo.avatar = "supertest.png"
        assertEquals(newUserInfo, result)
    }

    @Test
    fun test_3_Update() = runBlockingTest {
        val newUserInfo = userInfo.copy()
        newUserInfo.email = "TEST@test.fr"
        newUserInfo.avatar = "supertest.png"
        newUserInfo.firstName = "SUPERTEST"
        newUserInfo.lastName = "supertest"

        val result = userInfoRepository.update(newUserInfo)

        assertEquals(newUserInfo, result)
    }
}