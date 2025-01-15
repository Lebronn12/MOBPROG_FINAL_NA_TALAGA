package com.example.mobprog_final_na_talaga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutAdapter(
    private val workouts: List<Workout>,
    private val onWorkoutClick: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(workouts[position], onWorkoutClick)
    }

    override fun getItemCount(): Int = workouts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val workoutTextView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(workout: Workout, onClick: (Workout) -> Unit) {
            workoutTextView.text = workout.workoutName
            itemView.setOnClickListener { onClick(workout) }
        }
    }
}
