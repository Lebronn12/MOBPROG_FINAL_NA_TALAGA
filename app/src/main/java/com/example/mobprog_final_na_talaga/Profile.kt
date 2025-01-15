package com.example.mobprog_final_na_talaga

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Profile : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var fullNameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        databaseHelper = DatabaseHelper(this)

        fullNameTextView = findViewById(R.id.textViewFullName)
        usernameTextView = findViewById(R.id.textViewUsername)
        emailTextView = findViewById(R.id.textViewEmail)
        ageTextView = findViewById(R.id.textViewAge)
        heightTextView = findViewById(R.id.textViewHeight)
        weightTextView = findViewById(R.id.textViewWeight)

        val userId = intent.getIntExtra("USER_ID", 0)

        val user = databaseHelper.getUserById(userId)

        user?.let {
            fullNameTextView.text = "${it.firstName} ${it.lastName}"
            usernameTextView.text = it.username
            emailTextView.text = it.email
            ageTextView.text = it.age.toString()
            heightTextView.text = it.height.toString()
            weightTextView.text = it.weight.toString()
        } ?: run {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }
    }
}
