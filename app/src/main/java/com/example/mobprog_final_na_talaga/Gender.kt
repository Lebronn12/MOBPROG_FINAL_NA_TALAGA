package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Gender : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int = 0
    private var username: String? = null // To hold the username

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gender)

        dbHelper = DatabaseHelper(this)
        userId = intent.getIntExtra("USER_ID", 0)
        username = intent.getStringExtra("USERNAME")

        val maleRadioButton = findViewById<RadioButton>(R.id.radioMale)
        val femaleRadioButton = findViewById<RadioButton>(R.id.radioFemale)
        val nextButton = findViewById<Button>(R.id.Next)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nextButton.setOnClickListener {
            val gender = when {
                maleRadioButton.isChecked -> "Male"
                femaleRadioButton.isChecked -> "Female"
                else -> null
            }

            if (gender != null) {
                dbHelper.updateGender(userId, gender)

                val intent = Intent(this, MainGoal::class.java)
                intent.putExtra("USER_ID", userId)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select your gender.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
