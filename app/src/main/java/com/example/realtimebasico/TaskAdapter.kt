package com.example.realtimebasico

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.realtimebasico.databinding.TaskItemBinding

class TaskAdapter(val onItemSelected: (reference: String) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val taskList = mutableListOf<Pair<String, Task>>()

    fun updateList(data: List<Pair<String, Task>>) {
        taskList.clear()
        taskList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = taskList.count()

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = taskList[position]
        holder.bind(item.first, item.second)
    }

    inner class TaskViewHolder(private val view: TaskItemBinding) : ViewHolder(view.root) {

        fun bind(referenceTask: String, task: Task) {
            view.apply {
                title.text = task.title
                description.text = task.description
                reference.text = referenceTask
                root.setOnClickListener {
                    onItemSelected.invoke(referenceTask)
                }
            }
        }
    }
}