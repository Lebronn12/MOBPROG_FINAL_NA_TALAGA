package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainGoal : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int = 0
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_goal)

        dbHelper = DatabaseHelper(this)
        userId = intent.getIntExtra("USER_ID", 0)
        username = intent.getStringExtra("USERNAME")
        val weightLossRadioButton = findViewById<RadioButton>(R.id.radioLoseWeight)
        val muscleGainRadioButton = findViewById<RadioButton>(R.id.radioBuildMuscle)
        val maintenanceRadioButton = findViewById<RadioButton>(R.id.radioStayFit)
        val nextButton = findViewById<Button>(R.id.Next)

        addInitialGoals()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nextButton.setOnClickListener {
            val selectedGoal = when {
                weightLossRadioButton.isChecked -> "Lose Weight"
                muscleGainRadioButton.isChecked -> "Gain Muscle"
                maintenanceRadioButton.isChecked -> "Maintain Weight"
                else -> null
            }

            if (selectedGoal != null) {
                val goalId = dbHelper.getGoalIdByName(selectedGoal)

                if (goalId != null) {
                    dbHelper.updateMainGoal(userId, goalId)

                    val intent = Intent(this, Age::class.java)
                    intent.putExtra("USER_ID", userId)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Goal not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please select a goal.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addInitialGoals() {
        val goals = listOf("Gain Muscle", "Maintain Weight", "Lose Weight")

        for (goal in goals) {
            val newGoalId = dbHelper.addGoal(goal)
            if (newGoalId != -1L) {
                Log.d("Goal Addition", "Successfully added goal: $goal with ID: $newGoalId")
            } else {
                Log.e("Goal Addition", "Failed to add goal: $goal")
            }
        }
    }

}
