package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class Login : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val signupTextView = findViewById<TextView>(R.id.signupTextView)
        val signupText = getString(R.string.signup)
        val spannableString = SpannableString(signupText)
        val startIndex = signupText.indexOf("Sign up now!")
        val endIndex = startIndex + "Sign up now!".length
        spannableString.setSpan(UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FF69B4")), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        signupTextView.text = spannableString
        signupTextView.movementMethod = LinkMovementMethod.getInstance()

        dbHelper = DatabaseHelper(this)
        val loginButton = findViewById<MaterialButton>(R.id.login)
        val usernameEditText = findViewById<EditText>(R.id.Username)
        val passwordEditText = findViewById<EditText>(R.id.Password)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {

                val isLoginSuccessful = dbHelper.verifyUser(username, password)
                if (isLoginSuccessful) {
                    Toast.makeText(this, "Login successful! Welcome, $username!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    intent.putExtra("USER_ID", userId)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        signupTextView.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}