package com.antigravity.prathamchikitse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antigravity.prathamchikitse.databinding.ItemEmergencyBinding
import com.antigravity.prathamchikitse.models.EmergencyTopic

class EmergencyAdapter(
    private val topics: List<EmergencyTopic>,
    private val onClick: (EmergencyTopic) -> Unit
) : RecyclerView.Adapter<EmergencyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemEmergencyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEmergencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topic = topics[position]
        holder.binding.tvTitle.text = topic.title
        holder.binding.ivIcon.setImageResource(topic.iconResId)
        holder.itemView.setOnClickListener { onClick(topic) }
    }

    override fun getItemCount() = topics.size
}
