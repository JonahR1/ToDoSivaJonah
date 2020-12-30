package com.sivajonah.todo.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class LoginForm(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String
)