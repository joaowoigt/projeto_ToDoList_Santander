package com.woigt.todolist.UI

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woigt.todolist.R
import com.woigt.todolist.databinding.ItemTaskBinding
import com.woigt.todolist.model.Task


class TaskListAdapter()
    : ListAdapter<Task,TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerDetail: (Task) -> Unit = {}
    var listenerEdit: (Task) -> Unit = {}
    var listenerCheckBox: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class TaskViewHolder(
        private val binding: ItemTaskBinding,

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.tvTaskTitle.text = item.title
            binding.tvTaskDescription.text = item.description
            binding.tvTaskDate.text = item.date
            binding.tvTaskTime.text = item.time
            binding.btDetail.setOnClickListener {
                showPopup(item)
            }
            binding.checkbox.setOnClickListener {
                if (!item.completed) {
                    item.completed = true
                    setStyle(item)
                } else {
                    item.completed = false
                    setStyle(item)
                }

            }


        }

        private fun setStyle(item: Task) {
            if (item.completed) {
                binding.tvTaskTitle.paintFlags = binding.tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTaskDescription.paintFlags = binding.tvTaskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTaskDate.paintFlags = binding.tvTaskDate.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTaskTime.paintFlags = binding.tvTaskTime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvTaskTitle.paintFlags = binding.tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvTaskDescription.paintFlags = binding.tvTaskDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvTaskDate.paintFlags = binding.tvTaskDate.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvTaskTime.paintFlags = binding.tvTaskTime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }
        }

        private fun showPopup(item: Task) {
            val detail = binding.btDetail
            val popupMenu = PopupMenu(detail.context, detail)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when( it.itemId) {
                    R.id.detail -> listenerDetail(item)
                    R.id.edit -> listenerEdit(item)
            }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()

        }

        }

        }





    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    }

