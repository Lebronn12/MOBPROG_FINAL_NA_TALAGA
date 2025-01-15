package com.example.mobprog_final_na_talaga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MuscleGroupAdapter(
    private val muscleGroups: List<String>,
    private val onMuscleGroupClick: (String) -> Unit
) : RecyclerView.Adapter<MuscleGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(muscleGroups[position], onMuscleGroupClick)
    }

    override fun getItemCount(): Int = muscleGroups.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val muscleGroupTextView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(muscleGroup: String, onClick: (String) -> Unit) {
            muscleGroupTextView.text = muscleGroup
            itemView.setOnClickListener { onClick(muscleGroup) }
        }
    }
}
