package com.sivajonah.todo.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("email")
    var email: String,
    @SerialName("firstname")
    var firstName: String,
    @SerialName("lastname")
    var lastName: String,
    @SerialName("avatar")
    var avatar: String = "https://goo.gl/gEgYUd"
)