package com.sivajonah.todo.tasklist
import java.io.Serializable
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Task(val id: String, val title: String, val description: String = "") : Serializable