package com.woigt.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Events.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.woigt.todolist.R
import com.woigt.todolist.databinding.ActivityDetailBinding
import com.woigt.todolist.localdata.Task
import com.woigt.todolist.localdata.TaskApplication
import com.woigt.todolist.viewmodel.TaskViewModel
import com.woigt.todolist.viewmodel.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity(): AppCompatActivity() {

     lateinit var task: Task

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).database.taskDao())
    }

    private fun deleteTask() {
        taskViewModel.deleteTask(task)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?, ) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra("id", 0)

         fun bind(task: Task) {
             binding.apply {
                 tv_detail_title.text = task.title
                 tv_detail_description.text = task.description
                 tv_detail_date.text = task.date
                 tv_detail_time.text = task.time

             }
         }

        taskViewModel.retrieveTask(id).observe(this, Observer { selectedItem ->
            task = selectedItem
            bind(task) })

        insertListeners()
    }

    private fun insertListeners() {

        bt_edit.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("id", task.id)
            startActivity(intent)
        }

        bt_back.setOnClickListener {
            finish()
        }

        bt_delete.setOnClickListener {
            deleteTask()
        }

        bt_schedule.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT)
                    .setData(CONTENT_URI)
                    .putExtra(TITLE, task.title)
                    .putExtra(DESCRIPTION, task.description)
            startActivity(intent)
            }

        }

}
