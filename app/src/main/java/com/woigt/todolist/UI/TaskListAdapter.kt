package com.woigt.todolist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.woigt.todolist.R
import com.woigt.todolist.localdata.Task

class TaskListAdapter :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.tv_task_title)
        private val taskDescription: TextView = itemView.findViewById(R.id.tv_task_description)
        private val taskDate: TextView = itemView.findViewById(R.id.tv_task_date)
        private val taskTime: TextView = itemView.findViewById(R.id.tv_task_time)

        fun bind( item: Task) {
            taskTitle.text = item.title
            taskDescription.text = item.description
            taskDate.text = item.date
            taskTime.text = item.time
        }

        companion object {
            fun create(parent: ViewGroup): TaskViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_task, parent, false)
                return TaskViewHolder(view)
            }
        }
    }

    class TaskComparator : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title
        }

    }
}