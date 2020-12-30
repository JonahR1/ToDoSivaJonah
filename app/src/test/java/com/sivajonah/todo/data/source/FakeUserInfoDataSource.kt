package com.sivajonah.todo.data.source

import com.sivajonah.todo.authentication.LoginForm
import com.sivajonah.todo.authentication.LoginResponse
import com.sivajonah.todo.authentication.SignUpForm
import com.sivajonah.todo.network.UserInfo
import com.sivajonah.todo.network.UserWebService
import okhttp3.MultipartBody
import retrofit2.Response

class FakeUserInfoDataSource(var userInfo: MutableList<UserInfo>? = mutableListOf()) : UserWebService {
    override suspend fun getInfo(): Response<UserInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAvatar(avatar: MultipartBody.Part): Response<UserInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun update(user: UserInfo): Response<UserInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun login(user: LoginForm): Response<LoginResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(user: SignUpForm): Response<LoginResponse> {
        TODO("Not yet implemented")
    }
}