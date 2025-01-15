package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Weight : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int = 0
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weight)

        dbHelper = DatabaseHelper(this)
        userId = intent.getIntExtra("USER_ID", 0)
        username = intent.getStringExtra("USERNAME")
        val editCurrentWeight = findViewById<EditText>(R.id.Current_weight)
        val editTargetWeight = findViewById<EditText>(R.id.Target_weight)
        val nextButton = findViewById<Button>(R.id.Next)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nextButton.setOnClickListener {
            val currentWeightString = editCurrentWeight.text.toString()
            val targetWeightString = editTargetWeight.text.toString()

            if (currentWeightString.isNotEmpty() && targetWeightString.isNotEmpty()) {
                val currentWeight = currentWeightString.toDouble()
                val targetWeight = targetWeightString.toDouble()

                dbHelper.updateWeight(userId, currentWeight, targetWeight)

                // Move to the next activity
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("USER_ID", userId)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter both weights.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}