package com.example.e_montaysta.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montaysta.data.model.Tool
import com.example.e_montaysta.databinding.RecycleviewToolBinding


class ToolsAdapter(private val tools: List<Tool>) : RecyclerView.Adapter<ToolsAdapter.ToolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecycleviewToolBinding.inflate(inflater, parent, false)
        return ToolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToolViewHolder, position: Int) {
        val tool = tools[position]
        holder.bind(tool)
    }

    override fun getItemCount(): Int {
        return tools.size
    }

    class ToolViewHolder(private val binding: RecycleviewToolBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tool: Tool) {
            binding.toolName.text = tool.name
            binding.toolCode.text = tool.code
        }
    }
}

