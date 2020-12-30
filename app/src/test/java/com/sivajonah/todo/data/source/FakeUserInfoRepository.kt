package com.sivajonah.todo.data.source

import com.sivajonah.todo.network.UserInfo
import com.sivajonah.todo.network.UserInfoRepositoryInterface
import okhttp3.MultipartBody

class FakeUserInfoRepository : UserInfoRepositoryInterface {
    lateinit var userInfo : UserInfo

    override suspend fun getInfo(): UserInfo? {
        userInfo?.let {
            return userInfo
        }
        throw Exception("UserInfo not found")
    }

    override suspend fun updateAvatar(avatar: MultipartBody.Part): UserInfo? {
        userInfo?.let {
            val newAvatar = avatar.headers.toString().split(";")[2].split("\"")[1]
            userInfo!!.avatar = newAvatar
            return userInfo
        }
        throw Exception("Fail to update avatar of userInfo")
    }

    override suspend fun update(user: UserInfo): UserInfo? {
        userInfo?.let {
            userInfo = user.copy()
            return userInfo
        }
    }

    fun addUserInfo(user: UserInfo): FakeUserInfoRepository {
        userInfo = user.copy()
        return this
    }
}