package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.random.Random

class Activity_Tracking : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var linearProgressBarIndicator: ProgressBar
    private lateinit var progressPercentageText: TextView
    private lateinit var stepsTextView: TextView
    private lateinit var caloriesTextView: TextView
    private lateinit var distanceTextView: TextView
    private var running = false
    private val handler = Handler(Looper.getMainLooper())
    private var totalCalories = 1000
    private var currentCalories: Float = 0f
    private var steps = 0
    private var distance = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tracking)

        dbHelper = DatabaseHelper(this)

        linearProgressBarIndicator = findViewById(R.id.linearProgressBarIndicator)
        progressPercentageText = findViewById(R.id.progressPercentage)
        stepsTextView = findViewById(R.id.stepsTextView)
        caloriesTextView = findViewById(R.id.caloriesTextView)
        distanceTextView = findViewById(R.id.distanceTextView)

        val userId = 1

        findViewById<MaterialButton>(R.id.logActivityButton).setOnClickListener {
            logActivity(userId)
        }

        findViewById<MaterialButton>(R.id.startTrackingButton).setOnClickListener {
            startTrackingService()
        }

        findViewById<MaterialButton>(R.id.stopTrackingButton).setOnClickListener {
            stopTrackingService()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun logActivity(userId: Int) {
        val startTime = "2024-10-25T10:00:00"
        val endTime = "2024-10-25T11:00:00"
        val caloriesBurned = currentCalories

        val result = dbHelper.insertActivity(userId, null, null, startTime, endTime, caloriesBurned)

        if (result > -1) {
            Toast.makeText(this, "Activity logged successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to log activity.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTrackingService() {
        val serviceIntent = Intent(this, ActivityTrackingService::class.java)
        startService(serviceIntent)
        Toast.makeText(this, "Tracking started", Toast.LENGTH_SHORT).show()
        Log.d("ActivityTracking", "Service started")


        linearProgressBarIndicator.visibility = ProgressBar.VISIBLE
        linearProgressBarIndicator.progress = 0
        progressPercentageText.text = "0%"
        stepsTextView.text = "Steps: 0"
        caloriesTextView.text = "Calories: 0"
        distanceTextView.text = "Distance: 0 m"

        running = true
        updateTrackingProgress()
    }

    private fun stopTrackingService() {
        val serviceIntent = Intent(this, ActivityTrackingService::class.java)
        stopService(serviceIntent)
        Toast.makeText(this, "Tracking stopped", Toast.LENGTH_SHORT).show()
        Log.d("ActivityTracking", "Service stopped")

        linearProgressBarIndicator.visibility = ProgressBar.GONE
        running = false
    }

    private fun updateTrackingProgress() {
        if (running) {
            steps += Random.nextInt(1, 6)
            currentCalories += Random.nextFloat() * (1.5f - 0.5f) + 0.5f // Increment calories randomly between 0.5 and 1.5
            distance += Random.nextFloat() * (0.5f - 0.1f) + 0.1f // Increment distance randomly between 0.1 and 0.5

            // Update UI
            stepsTextView.text = "Steps: $steps"
            caloriesTextView.text = "Calories: ${currentCalories.toInt()}"
            distanceTextView.text = "Distance: ${distance.toInt()} m"

            val progress = ((currentCalories / totalCalories) * 100).toInt().coerceIn(0, 100)
            linearProgressBarIndicator.progress = progress
            progressPercentageText.text = "$progress%"

            handler.postDelayed({ updateTrackingProgress() }, 1000)
        }
    }

    private fun loadUserActivities(userId: Int) {
        val cursor = dbHelper.getUserActivities(userId)

        val columnNames = cursor.columnNames
        Log.d("ActivityTracking", "Columns: ${columnNames.joinToString(", ")}")

        if (cursor.moveToFirst()) {
            do {
                val activityIdIndex = cursor.getColumnIndex("activity_id")
                val startTimeIndex = cursor.getColumnIndex("start_time")
                val endTimeIndex = cursor.getColumnIndex("end_time")
                val caloriesBurnedIndex = cursor.getColumnIndex("calories_burned")

                if (activityIdIndex != -1 && startTimeIndex != -1 && endTimeIndex != -1 && caloriesBurnedIndex != -1) {
                    val activityId = cursor.getInt(activityIdIndex)
                    val startTime = cursor.getString(startTimeIndex)
                    val endTime = cursor.getString(endTimeIndex)
                    val caloriesBurned = cursor.getFloat(caloriesBurnedIndex)

                    Log.d("ActivityTracking", "Activity ID: $activityId, Start: $startTime, End: $endTime, Calories: $caloriesBurned")
                } else {
                    Log.e("Database Error", "One or more columns not found.")
                }

            } while (cursor.moveToNext())
        } else {
            Log.d("ActivityTracking", "No activities found for user ID: $userId")
        }

        cursor.close()
    }
}
