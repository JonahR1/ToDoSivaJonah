package com.sivajonah.todo.network

import com.sivajonah.todo.authentication.LoginForm
import com.sivajonah.todo.authentication.LoginResponse
import com.sivajonah.todo.authentication.SignUpForm
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserWebService {
    @GET("users/info")
    suspend fun getInfo(): Response<UserInfo>

    @Multipart
    @PATCH("users/update_avatar")
    suspend fun updateAvatar(@Part avatar: MultipartBody.Part): Response<UserInfo>

    @PATCH("users")
    suspend fun update(@Body user: UserInfo): Response<UserInfo>

    @POST("users/login")
    suspend fun login(@Body user: LoginForm): Response<LoginResponse>

    @POST("users/sign_up")
    suspend fun signUp(@Body user: SignUpForm): Response<LoginResponse>
}