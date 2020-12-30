package com.sivajonah.todo.network

import okhttp3.MultipartBody

interface UserInfoRepositoryInterface {
    suspend fun getInfo(): UserInfo?

    suspend fun updateAvatar(avatar: MultipartBody.Part) : UserInfo?

    suspend fun update(user: UserInfo) : UserInfo?
}