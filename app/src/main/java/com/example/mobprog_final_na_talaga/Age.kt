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

class Age : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int = 0
    private var username: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_age)

        dbHelper = DatabaseHelper(this)
        userId = intent.getIntExtra("USER_ID", 0)
        username = intent.getStringExtra("USERNAME")
        val ageEditText = findViewById<EditText>(R.id.Age)
        val nextButton = findViewById<Button>(R.id.Next)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nextButton.setOnClickListener {
            val ageString = ageEditText.text.toString()

            if (ageString.isNotEmpty()) {
                val age = ageString.toInt()

                dbHelper.updateAge(userId, age)

                val intent = Intent(this, Height::class.java)
                intent.putExtra("USER_ID", userId)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter your age.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }
