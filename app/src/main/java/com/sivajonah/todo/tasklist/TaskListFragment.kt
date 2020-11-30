package com.sivajonah.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sivajonah.todo.R
import com.sivajonah.todo.task.TaskActivity
import com.sivajonah.todo.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import java.util.*

class TaskListFragment : Fragment() {
    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
        taskList.add(task)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        return rootView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pour une [RecyclerView] ayant l'id "recycler_view":
        var recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view);
        recyclerView?.layoutManager = LinearLayoutManager(activity);
        recyclerView?.adapter = TaskListAdapter(taskList);

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)

            // Instanciation d'un objet task avec des données préremplies:
            /*taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
            recyclerView?.adapter?.notifyDataSetChanged();*/
        }

        // "implémentation" de la lambda dans le fragment:
        (recyclerView?.adapter as TaskListAdapter)?.onDeleteClickListener = { task ->
            // Supprimer la tâche
            taskList.remove(task);
            recyclerView?.adapter?.notifyDataSetChanged();
        }
    }
}