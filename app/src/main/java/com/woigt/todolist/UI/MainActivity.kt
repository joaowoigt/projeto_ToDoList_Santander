package com.woigt.todolist.UI

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.google.gson.Gson
import com.woigt.todolist.databinding.ActivityMainBinding
import com.woigt.todolist.datasource.TaskDataSource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_task.*




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvTasks.adapter = adapter
        updateList()

        insertListeners()
    }

    private fun insertListeners() {
        binding.btNewTodo.setOnClickListener {
            startActivityForResult(
                Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK
            )
        }

        binding.includeEmpty.btNewTodoEmpty.setOnClickListener {
            startActivityForResult(
                Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK
            )
        }


        adapter.listenerDetail = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivity(intent)
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()

    }

    private fun updateList() {
        val list = TaskDataSource.getList()
        if (list.isEmpty()) {
            binding.ivLogo.visibility = View.GONE
            binding.rvTasks.visibility = View.GONE
            binding.btNewTodo.visibility = View.GONE
            binding.includeEmpty.ivLogoEmpty.visibility = View.VISIBLE
            binding.includeEmpty.btNewTodoEmpty.visibility = View.VISIBLE
        }else {
            binding.ivLogo.visibility = View.VISIBLE
            binding.rvTasks.visibility = View.VISIBLE
            binding.btNewTodo.visibility = View.VISIBLE
            binding.includeEmpty.ivLogoEmpty.visibility = View.GONE
            binding.includeEmpty.btNewTodoEmpty.visibility = View.GONE
        }

        adapter.submitList(list)

    }
    companion object {
        private const val CREATE_NEW_TASK = 1000
        private const val DETAIL_TASK = 2000
    }
}
