package com.antigravity.prathamchikitse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.antigravity.prathamchikitse.databinding.FragmentGuideStepBinding
import com.antigravity.prathamchikitse.models.GuideStep

class GuidePagerAdapter(
    private val context: AppCompatActivity,
    private val steps: List<GuideStep>,
    var isKannada: Boolean
) : RecyclerView.Adapter<GuidePagerAdapter.StepViewHolder>() {

    class StepViewHolder(val binding: FragmentGuideStepBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = FragmentGuideStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]
        holder.binding.tvStepNumber.text = "STEP ${step.stepNumber} OF ${steps.size}"
        
        if (isKannada) {
            holder.binding.tvStepTitle.text = step.titleKa
            holder.binding.tvStepDescription.text = step.descriptionKa
        } else {
            holder.binding.tvStepTitle.text = step.titleEn
            holder.binding.tvStepDescription.text = step.descriptionEn
        }
    }

    override fun getItemCount(): Int = steps.size
}
