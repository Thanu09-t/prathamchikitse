package com.antigravity.prathamchikitse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antigravity.prathamchikitse.databinding.ItemHospitalBinding
import com.antigravity.prathamchikitse.models.Hospital

class HospitalAdapter(
    private var hospitals: List<Hospital>,
    private val onDirectionsClick: (Hospital) -> Unit
) : RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemHospitalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val h = hospitals[position]
        holder.binding.tvHospitalName.text = h.name
        holder.binding.tvDistance.text = formatDistance(h.distanceMeters)
        holder.binding.tvAddress.text = if (h.address.isNotBlank()) h.address else "Tap for directions"
        holder.binding.tvPhone.text = if (h.phone.isNotBlank()) "📞 ${h.phone}" else ""
        holder.binding.tvRank.text = "${position + 1}"
        holder.binding.btnDirections.setOnClickListener { onDirectionsClick(h) }
        holder.itemView.setOnClickListener { onDirectionsClick(h) }
    }

    override fun getItemCount() = hospitals.size

    fun updateData(newList: List<Hospital>) {
        hospitals = newList
        notifyDataSetChanged()
    }

    private fun formatDistance(meters: Float): String {
        return if (meters < 1000) {
            "${meters.toInt()} m away"
        } else {
            String.format("%.1f km away", meters / 1000f)
        }
    }
}
