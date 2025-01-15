package com.example.mobprog_final_na_talaga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutSetAdapter(
    private val workoutSets: List<WorkoutSet>
) : RecyclerView.Adapter<WorkoutSetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(workoutSets[position])
    }

    override fun getItemCount(): Int = workoutSets.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val repsTextView: TextView = itemView.findViewById(android.R.id.text1)
        private val weightTextView: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(workoutSet: WorkoutSet) {
            repsTextView.text = "Reps: ${workoutSet.reps}"
            weightTextView.text = "Weight: ${workoutSet.weight} kg | Rest: ${workoutSet.restTime} sec"
        }
    }
}
