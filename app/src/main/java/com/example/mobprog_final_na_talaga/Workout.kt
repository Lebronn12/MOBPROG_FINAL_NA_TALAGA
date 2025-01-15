package com.example.mobprog_final_na_talaga

data class Workout(
    val workoutId: Int,
    val workoutName: String,
    val description: String,
    val duration: Int
)
data class WorkoutSet(
    val setId: Int,
    val workoutId: Int,
    val reps: Int,
    val weight: Float,
    val restTime: Int
)