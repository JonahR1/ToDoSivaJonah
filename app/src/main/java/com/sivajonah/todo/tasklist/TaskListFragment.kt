package com.sivajonah.todo.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sivajonah.todo.R
import com.sivajonah.todo.network.Api
import com.sivajonah.todo.task.TaskActivity
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer
import coil.load
import coil.transform.CircleCropTransformation
import com.sivajonah.todo.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.sivajonah.todo.userinfo.UserInfoActivity
import com.sivajonah.todo.userinfo.UserInfoViewModel
import okhttp3.internal.wait

class TaskListFragment : Fragment() {
    private val taskListViewModel: TaskListViewModel by viewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()
    private val adapter = TaskListAdapter()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TaskActivity.ADD_TASK_REQUEST_CODE  && resultCode == Activity.RESULT_OK) {
            val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            taskListViewModel.createTask(task)
        } else if (requestCode == TaskActivity.EDIT_TASK_REQUEST_CODE  && resultCode == Activity.RESULT_OK) {
            val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            taskListViewModel.updateTask(task)
        }
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
        recyclerView?.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)

            // Instanciation d'un objet task avec des données préremplies:
            /*taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
            recyclerView?.adapter?.notifyDataSetChanged();*/
        }

        // "implémentation" de la lambda dans le fragment:
        adapter.onDeleteClickListener = { task ->
            taskListViewModel.deleteTask(task)
            adapter.notifyDataSetChanged()
        }

        adapter.onEditClickListener = { task ->
            val intent = Intent(activity, TaskActivity::class.java)
            intent.putExtra(TaskActivity.TASK_KEY, task)
            startActivityForResult(intent, TaskActivity.EDIT_TASK_REQUEST_CODE)
        }

        taskListViewModel.taskList.observe(viewLifecycleOwner, Observer {
            adapter.taskList = it.toMutableList()
            adapter.notifyDataSetChanged();
        })
    }

    override fun onResume() {
        super.onResume()
        val imageView = view?.findViewById<ImageView>(R.id.imageView)

        // Ici on ne va pas gérer les cas d'erreur donc on force le crash avec "!!"
        lifecycleScope.launch {
            userInfoViewModel.getInfo()!!
        }

        userInfoViewModel.userInfo.observe(viewLifecycleOwner, Observer { userInfo ->
            view?.findViewById<TextView>(R.id.textView)?.text = "${userInfo.firstName} ${userInfo.lastName}"

            imageView?.load(userInfo.avatar) {
                transformations(CircleCropTransformation())
                size(400)
            }

            // au cas ou l'image download ne fonctionne pas
            if(imageView?.isVisible == false) {
                imageView?.load("https://goo.gl/gEgYUd") {
                    transformations(CircleCropTransformation())
                    size(250)
                }
            }
        })

        imageView?.setOnClickListener {
            startActivity(Intent(activity, UserInfoActivity::class.java))
        }
        taskListViewModel.refresh()
    }
}