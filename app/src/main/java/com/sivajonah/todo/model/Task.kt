package com.sivajonah.todo.model
import java.io.Serializable
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Task(val id: String, var title: String, var description: String = "") : Serializable