package com.sivajonah.todo.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SignupResponse(
    @SerialName("token")
    val token: String
)