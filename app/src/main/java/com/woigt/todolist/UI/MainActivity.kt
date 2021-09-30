package com.woigt.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.woigt.todolist.databinding.ActivityMainBinding
import com.woigt.todolist.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main Activity for the todoapp
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val taskViewModel: TaskViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}

