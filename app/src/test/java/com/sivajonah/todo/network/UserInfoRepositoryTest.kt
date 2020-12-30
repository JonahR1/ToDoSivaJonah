package com.sivajonah.todo.network

import com.sivajonah.todo.data.source.FakeTasksDataSource
import com.sivajonah.todo.data.source.FakeUserInfoDataSource
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserInfoRepositoryTest : TestCase() {
    private val userInfo = UserInfo("test@test.fr", "TEST", "test", "test.png")

    private var userInfoDataSource: FakeUserInfoDataSource = FakeUserInfoDataSource(userInfo)
    private var userInfoRepository: UserInfoRepository = UserInfoRepository(userInfoDataSource)

    fun test_1_GetInfo() = runBlockingTest {
        val result = userInfoRepository.getInfo()

        assertEquals(userInfo, result)
    }

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