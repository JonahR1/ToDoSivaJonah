package com.sivajonah.todo.repository

import com.sivajonah.todo.model.UserInfo
import okhttp3.MultipartBody

interface UserInfoRepositoryInterface {
    suspend fun getInfo(): UserInfo?

    suspend fun updateAvatar(avatar: MultipartBody.Part) : UserInfo?

    suspend fun update(user: UserInfo) : UserInfo?
}