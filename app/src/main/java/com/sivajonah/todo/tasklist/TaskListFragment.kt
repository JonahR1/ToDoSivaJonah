package com.sivajonah.todo.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.sivajonah.todo.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.sivajonah.todo.userinfo.UserInfoActivity

class TaskListFragment : Fragment() {
    /*private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )*/

    private val viewModel: TaskListViewModel by viewModels()
    val adapter = TaskListAdapter()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TaskActivity.ADD_TASK_REQUEST_CODE  && resultCode == Activity.RESULT_OK) {
            val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            viewModel.createTask(task)
        } else if (requestCode == TaskActivity.EDIT_TASK_REQUEST_CODE  && resultCode == Activity.RESULT_OK) {
            val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            viewModel.updateTask(task)
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
            viewModel.deleteTask(task)
            adapter.notifyDataSetChanged()
        }

        adapter.onEditClickListener = { task ->
            val intent = Intent(activity, TaskActivity::class.java)
            intent.putExtra(TaskActivity.TASK_KEY, task)
            startActivityForResult(intent, TaskActivity.EDIT_TASK_REQUEST_CODE)
        }

        viewModel.taskList.observe(viewLifecycleOwner, Observer {
            adapter.taskList = it.toMutableList()
            adapter.notifyDataSetChanged();
        })
    }

    override fun onResume() {
        super.onResume()

        // Ici on ne va pas gérer les cas d'erreur donc on force le crash avec "!!"
        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            view?.findViewById<TextView>(R.id.textView)?.text = "${userInfo.firstName} ${userInfo.lastName}"
            viewModel.refresh()
        }

        val imageView = view?.findViewById<ImageView>(R.id.imageView)

        imageView?.load("https://goo.gl/gEgYUd")
/*        val bitmap = imageView?.getDrawable()!!.toBitmap()
        val imageRounded = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(imageRounded)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        canvas.drawRoundRect(RectF(10F, 10F, bitmap.width.toFloat(), bitmap.height.toFloat()),
            200F, 200F, paint) // Round Image Corner 100 100 100 100
        imageView.setImageBitmap(imageRounded)*/

        imageView?.setOnClickListener {
            startActivity(Intent(activity, UserInfoActivity::class.java))
        }

    }
}