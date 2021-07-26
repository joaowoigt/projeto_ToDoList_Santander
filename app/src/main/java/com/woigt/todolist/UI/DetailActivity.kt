package com.woigt.todolist.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.woigt.todolist.R
import com.woigt.todolist.UI.AddTaskActivity.Companion.TASK_ID
import com.woigt.todolist.databinding.ActivityDetailBinding
import com.woigt.todolist.datasource.TaskDataSource
import com.woigt.todolist.extensions.text
import com.woigt.todolist.model.Task
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity: AppCompatActivity() {

    private  var task: Task? = null
    private  var taskDataSource: TaskDataSource? = null

    lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?, ) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                tv_detail_title.text = it.title
                tv_detail_description.text = it.description
                tv_detail_date.text = it.date
                tv_detail_time.text = it.time
            }
        }



        insertListeners()
    }



    private fun insertListeners() {
        bt_back.setOnClickListener {
            taskDataSource?.deleteTask(task)
            finish()
        }

        bt_edit.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivity(intent)
        }
        bt_back.setOnClickListener {
            finish()
        }
    }





}
