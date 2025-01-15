package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class Register : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

        val firstNameEditText = findViewById<EditText>(R.id.First_Name)
        val lastNameEditText = findViewById<EditText>(R.id.Last_Name)
        val usernameEditText = findViewById<EditText>(R.id.RUsername)
        val emailEditText = findViewById<EditText>(R.id.REmail)
        val passwordEditText = findViewById<EditText>(R.id.RPassword)
        val registerButton = findViewById<MaterialButton>(R.id.Register)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (dbHelper.isUsernameExists(username)) {
                    Toast.makeText(this, "Username already exists. Please choose a different username.", Toast.LENGTH_SHORT).show()
                } else {
                    val result = dbHelper.insertUser(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        username = username,
                        password = password,
                        dateOfBirth = "",
                        gender = "",
                        weight = 0.0f,
                        targetWeight = 0.0f,
                        age = 0,
                        height = 0.0f,
                        goalId = 1
                    )

                    if (result > -1) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                        // Navigate to the Gender selection activity
                        val intent = Intent(this, Gender::class.java)
                        intent.putExtra("USERNAME", username)
                        startActivity(intent)

                        finish()
                    } else {
                        Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            }
        }


    }
}