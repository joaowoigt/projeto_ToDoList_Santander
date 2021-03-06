package com.woigt.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woigt.todolist.R
import com.woigt.todolist.databinding.ActivityMainBinding
import com.woigt.todolist.data.Task
import com.woigt.todolist.data.source.local.TaskApplication
import com.woigt.todolist.ui.tasks.AddEditTaskActivity
import com.woigt.todolist.ui.tasks.AddEditTaskActivity.Companion.EXTRA_COMPLETED
import com.woigt.todolist.ui.tasks.AddEditTaskActivity.Companion.EXTRA_DATE
import com.woigt.todolist.ui.tasks.AddEditTaskActivity.Companion.EXTRA_DESCRIPTION
import com.woigt.todolist.ui.tasks.AddEditTaskActivity.Companion.EXTRA_TIME
import com.woigt.todolist.ui.tasks.AddEditTaskActivity.Companion.EXTRA_TITLE
import com.woigt.todolist.ui.tasks.DetailActivity
import com.woigt.todolist.ui.tasks.TaskListAdapter
import com.woigt.todolist.viewmodel.TaskViewModel
import com.woigt.todolist.viewmodel.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity for the todoapp
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    private val newTaskActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_tasks)

        val adapter = TaskListAdapter({
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent) },taskViewModel)


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.allTasks.observe(this, { task ->
            task?.let { adapter.submitList(it) }})

        insertListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newTaskActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.extras?.let {
                val task = Task(it.getString(EXTRA_TITLE),
                it.getString(EXTRA_DESCRIPTION), it.getString(EXTRA_DATE),
                    it.getString(EXTRA_TIME), it.getBoolean(EXTRA_COMPLETED))
                taskViewModel.insertTask(task)
            }
        } else {
            Toast.makeText(applicationContext,"N??o salvo", Toast.LENGTH_LONG).show()
        }
    }

    private fun insertListeners() {
        binding.btNewTodo.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditTaskActivity::class.java)
            startActivityForResult(intent, newTaskActivityRequestCode)

        }

    }

}
