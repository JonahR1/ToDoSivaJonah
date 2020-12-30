package com.sivajonah.todo.repository

import com.sivajonah.todo.model.UserInfo
import com.sivajonah.todo.network.Api
import com.sivajonah.todo.webservice.UserWebService
import okhttp3.MultipartBody

class UserInfoRepository(private val userWebService : UserWebService = Api.INSTANCE.userWebService) :
    UserInfoRepositoryInterface {
    override suspend fun getInfo(): UserInfo? {
        val response = userWebService.getInfo()
        return if(response.isSuccessful) response.body() else null
    }

    override suspend fun updateAvatar(avatar: MultipartBody.Part) : UserInfo? {
        val response = userWebService.updateAvatar(avatar)
        return if(response.isSuccessful) response.body() else null
    }

    override suspend fun update(user: UserInfo) : UserInfo? {
        val response = userWebService.update(user)
        return if(response.isSuccessful) response.body() else null
    }
}