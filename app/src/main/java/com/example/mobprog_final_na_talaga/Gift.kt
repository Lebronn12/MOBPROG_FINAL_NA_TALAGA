package com.example.mobprog_final_na_talaga

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class Gift : AppCompatActivity() {

    private lateinit var rewardBar: ProgressBar
    private lateinit var stepsClaimButton: MaterialButton
    private lateinit var distanceClaimButton: MaterialButton
    private var stepsProgress = 0
    private var distanceProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gift)

        rewardBar = findViewById(R.id.RewardBar)
        stepsClaimButton = findViewById(R.id.steps_claim)
        distanceClaimButton = findViewById(R.id.distance_claim)

        stepsClaimButton.setOnClickListener {
            if (stepsProgress < 10000) {
                stepsProgress += 1000
                updateStepsProgress()
                Toast.makeText(this, "10,000 Steps Completed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Step task already completed", Toast.LENGTH_SHORT).show()
            }
        }

        distanceClaimButton.setOnClickListener {
            if (distanceProgress < 5) {
                distanceProgress += 1
                updateDistanceProgress()

                addDistanceReward()
                Toast.makeText(this, "Distance Quest Claim", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Distance task already completed", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun updateStepsProgress() {
        val progressPercentage = (stepsProgress.toFloat() / 10000 * 100).toInt()
        rewardBar.progress = progressPercentage
    }
    private fun updateDistanceProgress() {
        val progressPercentage = (distanceProgress.toFloat() / 5 * 100).toInt()
        if (progressPercentage < 100) {
            rewardBar.progress = 100
        }
    }

    private fun addDistanceReward() {
        val currentProgress = rewardBar.progress
        val newProgress = currentProgress + 20
        rewardBar.progress = if (newProgress > 100) 100 else newProgress
    }
}
