package com.sivajonah.todo.data.source

import com.sivajonah.todo.authentication.LoginForm
import com.sivajonah.todo.authentication.LoginResponse
import com.sivajonah.todo.authentication.SignUpForm
import com.sivajonah.todo.network.UserInfo
import com.sivajonah.todo.network.UserWebService
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Response.success

class FakeUserInfoDataSource(var userInfo: UserInfo?) : UserWebService {
    override suspend fun getInfo(): Response<UserInfo> {
        userInfo?.let {
            return success(userInfo)
        }

        return error(
            Exception("UserInfo not found")
        )
    }

    override suspend fun updateAvatar(avatar: MultipartBody.Part): Response<UserInfo> {
        userInfo?.let {
            val newAvatar = avatar.headers.toString().split(";")[2].split("\"")[1]
            userInfo!!.avatar = newAvatar
            return success(userInfo)
        }
        return error(
            Exception("Fail to update avatar of userInfo")
        )
    }

    override suspend fun update(user: UserInfo): Response<UserInfo> {
        userInfo?.let {
            userInfo = user.copy()
            return success(userInfo)
        }
        return error(
            Exception("Fail to update userInfo")
        )
    }

    override suspend fun login(user: LoginForm): Response<LoginResponse> {
        if(user.email == "test@test.fr" && user.password == "test") {
            return success(LoginResponse("fakeToken"))
        }
        return error(
            Exception("Fail to login")
        )
    }

    override suspend fun signUp(user: SignUpForm): Response<LoginResponse> {
        val condition = user.email.replace(" ", "") == "" &&
                user.firstname.replace(" ", "") == "" &&
                user.lastname.replace(" ", "") == "" &&
                user.password.replace(" ", "") == "" &&
                user.password == user.password_confirmation;

        if(condition)  {
            return success(LoginResponse("fakeToken"))
        }
        return error(
            Exception("Fail to signup")
        )
    }
}