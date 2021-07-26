package com.woigt.todolist.model

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

interface ClickItemTaskListener {
    fun clickItemTask(startActivityForResult: ActivityResultContracts.StartActivityForResult)
}