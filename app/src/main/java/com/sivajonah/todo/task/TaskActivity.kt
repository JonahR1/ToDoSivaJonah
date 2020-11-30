package com.sivajonah.todo.task

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.sivajonah.todo.R
import com.sivajonah.todo.tasklist.Task
import java.util.*

class TaskActivity : AppCompatActivity() {
    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
        const val TASK_KEY = "task_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_activity)

        this.findViewById<Button>(R.id.valider).setOnClickListener {
            // Instanciation d'un nouvel objet [Task]
            val newTask = Task(id = UUID.randomUUID().toString(), title = this.findViewById<EditText>(R.id.titre).text.toString(), description = this.findViewById<EditText>(R.id.description).text.toString())
            intent.putExtra(TASK_KEY,newTask)
            this.setResult(Activity.RESULT_OK, intent)
            this.finish()
        }
    }
}