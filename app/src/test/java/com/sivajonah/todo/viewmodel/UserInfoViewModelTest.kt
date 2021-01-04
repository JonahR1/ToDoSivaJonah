package com.sivajonah.todo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sivajonah.todo.data.source.FakeUserInfoRepository
import com.sivajonah.todo.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.*

import org.junit.Assert.*
import org.junit.rules.TestRule
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserInfoViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val userInfo = UserInfo("test@test.fr", "TEST", "test", "test.png")
    private var userInfoRepository : FakeUserInfoRepository = FakeUserInfoRepository()
    private var userInfoViewModel : UserInfoViewModel = UserInfoViewModel(userInfoRepository.addUserInfo(userInfo))

    companion object {
        private val testDispatcher = TestCoroutineDispatcher()
        private val testScope = TestCoroutineScope(testDispatcher)

        @BeforeClass
        @JvmStatic
        fun setup() {
            /* Set Coroutine Dispatcher. */
            Dispatchers.setMain(testDispatcher)
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            Dispatchers.resetMain()
            // Reset Coroutine Dispatcher and Scope.
            testDispatcher.cleanupTestCoroutines()
            testScope.cleanupTestCoroutines()
        }
    }

    @Test
    fun test_1_getInfo() = runBlockingTest {
        userInfoViewModel.getInfo()

        assertEquals(userInfo, userInfoViewModel.userInfo.value)
    }

    @Test
    fun test_2_updateAvatar() = runBlockingTest {
        val newAvatar =  MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "supertest.png",
            body = "hola".toRequestBody()
        )

        userInfoViewModel.updateAvatar(newAvatar)

        val newUserInfo = userInfo.copy()
        newUserInfo.avatar = "supertest.png"

        assertEquals(newUserInfo, userInfoViewModel.userInfo.value)
    }

    @Test
    fun test_3_update() = runBlockingTest {
        val newUserInfo = userInfo.copy()
        newUserInfo.email = "TEST@test.fr"
        newUserInfo.avatar = "supertest.png"
        newUserInfo.firstName = "SUPERTEST"
        newUserInfo.lastName = "supertest"

        userInfoViewModel.update(newUserInfo)

        assertEquals(newUserInfo, userInfoViewModel.userInfo.value)
    }
}