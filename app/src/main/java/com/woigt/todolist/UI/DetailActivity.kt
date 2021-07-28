package com.woigt.todolist.ui

import android.database.Cursor
import android.net.Uri
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.woigt.todolist.R
import com.woigt.todolist.database.TaskProvider.Companion.URI_TASKS
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.DATE_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.DESCRIPTION_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TIME_TASK
import com.woigt.todolist.database.TasksDatabaseHelper.Companion.TITLE_TASK

import com.woigt.todolist.databinding.ActivityDetailBinding

import com.woigt.todolist.localdata.Task
import com.woigt.todolist.ui.MainActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity(): AppCompatActivity() {

    private var id: Long = 0

    private var mCursor: Cursor? = null
    private  var task: Task? = null

    lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?, ) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail)

        val id = intent.getLongExtra(EXTRA_ID, 0)

        val uri = Uri.withAppendedPath(URI_TASKS, id.toString())
        val cursor = contentResolver?.query(uri, null,null, null, null)

        if (cursor?.moveToNext() as Boolean) {
            tv_detail_title.setText(cursor.getString(cursor.getColumnIndex(TITLE_TASK)))
            tv_detail_description.setText(cursor.getString(cursor.getColumnIndex(DESCRIPTION_TASK)))
            tv_detail_date.setText(cursor.getString(cursor.getColumnIndex(DATE_TASK)))
            tv_detail_time.setText(cursor.getString(cursor.getColumnIndex(TIME_TASK)))
        }


        cursor.close()
        insertListeners()


    }



    private fun insertListeners() {
        bt_back.setOnClickListener {
            finish()
        }



        bt_back.setOnClickListener {
            finish()
        }


    }





}

