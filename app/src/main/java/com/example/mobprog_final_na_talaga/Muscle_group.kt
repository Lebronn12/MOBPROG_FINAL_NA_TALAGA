package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Muscle_group : AppCompatActivity() {
    private lateinit var legsImage: View
    private lateinit var bicepImage: View
    private lateinit var backImage: View
    private lateinit var chestImage: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muscle_group)

        legsImage = findViewById(R.id.Legs)
        bicepImage = findViewById(R.id.bicep)
        backImage = findViewById(R.id.back)
        chestImage = findViewById(R.id.chest)
        legsImage.setOnClickListener { navigateToWorkoutDetail("Legs") }
        bicepImage.setOnClickListener { navigateToWorkoutDetail("Biceps") }
        backImage.setOnClickListener { navigateToWorkoutDetail("Back") }
        chestImage.setOnClickListener { navigateToWorkoutDetail("Chest") }

    }
    private fun navigateToWorkoutDetail(muscleGroup: String) {
        val intent = Intent(this, workout_detail::class.java).apply {
            putExtra("MUSCLE_GROUP", muscleGroup)
        }
        startActivity(intent)
    }

}
