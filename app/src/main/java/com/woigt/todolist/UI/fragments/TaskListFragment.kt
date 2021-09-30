package com.woigt.todolist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.woigt.todolist.databinding.TaskListFragmentBinding
import com.woigt.todolist.ui.taskAdapter.TaskListAdapter
import com.woigt.todolist.viewmodel.TaskViewModel
import org.koin.android.ext.android.inject

import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TaskListFragment: Fragment() {

    private lateinit var binding: TaskListFragmentBinding
    private val taskListAdapter: TaskListAdapter by inject()
    private val taskViewModel: TaskViewModel by sharedViewModel()
    private val controler by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = TaskListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setListeners()
    }

    private fun setListeners() {
        binding.btNewTodoAnimated.setOnClickListener {
            val direction = TaskListFragmentDirections.actionListFragmentToAddFragment()
            controler.navigate(direction)
        }

    }

    private fun setupRecyclerView() {
        binding.rvTasks.run {
            adapter = taskListAdapter
        }
        taskListAdapter.taskViewModel = taskViewModel

        taskListAdapter.onItemClicked = {
            val direction = TaskListFragmentDirections.actionListFragmentToDetailFragment(it.id)
            controler.navigate(direction)
        }

        taskViewModel.allTasks.observe(viewLifecycleOwner, { task ->
            task?.let { taskListAdapter.submitList(it)}
        })
    }
}
