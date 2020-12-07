package com.sivajonah.todo.tasklist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.sivajonah.todo.R
import com.sivajonah.todo.task.TaskActivity

class TaskListAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: Task) {
            itemView.apply { // `apply {}` permet d'éviter de répéter `itemView.*`
                // TODO: afficher les données et attacher les listeners aux différentes vues de notre [itemView]
                findViewById<TextView>(R.id.task_title).setText(taskTitle.title);
                findViewById<TextView>(R.id.task_description).setText(taskTitle.description);
                findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
                    // Utilisation de la lambda dans le ViewHolder:
                    onDeleteClickListener?.invoke(taskTitle)
                }
                findViewById<ImageButton>(R.id.editButton).setOnClickListener {
                    onEditClickListener?.invoke(taskTitle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return this.taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(this.taskList.get(position))
    }

    // Déclaration de la variable lambda dans l'adapter:
    var onDeleteClickListener: ((Task) -> Unit)? = null
    var onEditClickListener: ((Task) -> Unit)? = null
}
