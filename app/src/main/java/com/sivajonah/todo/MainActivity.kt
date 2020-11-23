package com.sivajonah.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sivajonah.todo.tasklist.Task
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}