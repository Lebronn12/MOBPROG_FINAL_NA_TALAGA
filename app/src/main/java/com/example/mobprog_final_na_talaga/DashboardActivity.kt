package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var userTextView: TextView
    private var username: String? = null
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        val imageView4: ImageView = findViewById(R.id.add)
        imageView4.setOnClickListener {

            val intent = Intent(this, Add_Activity::class.java)
            startActivity(intent)
        }

        val imageView5: ImageView = findViewById(R.id.collection)
        imageView5.setOnClickListener {

            val intent = Intent(this, CollectionActivity::class.java)
            startActivity(intent)
        }

        val imageView6: ImageView = findViewById(R.id.Rewards)

        imageView6.setOnClickListener{
            val intent = Intent(this, Gift::class.java)
            startActivity(intent)
        }


        databaseHelper = DatabaseHelper(this)


        userTextView = findViewById(R.id.userTextView)


        username = intent.getStringExtra("USERNAME")
        userId = intent.getIntExtra("USER_ID", 0)

        Log.d("DashboardActivity", "Received username: $username")

        if (username.isNullOrEmpty()) {
            Log.e("DashboardActivity", "Error: Username is missing.")
            Toast.makeText(this, "Error: Username is missing.", Toast.LENGTH_SHORT).show()
            return
        }


        val user = username?.let { databaseHelper.getUserByUsername(it) }


        if (user != null) {
            userTextView.text = "Welcome, ${user.firstName}!"
        } else {
            Log.e("DashboardActivity", "User not found for username: $username")
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            return
        }
        setupNavigation()
        setupProfileNavigation()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupNavigation() {
        val imageView: ImageView = findViewById(R.id.tracker)
        imageView.setOnClickListener {

            val intent = Intent(this, Activity_Tracking::class.java)
            startActivity(intent)
        }

        val imageView2: ImageView = findViewById(R.id.settings)
        imageView2.setOnClickListener {

            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        val imageView3: ImageView = findViewById(R.id.homeButton)
        imageView3.setOnClickListener {

            Log.d("DashboardActivity", "Home button clicked. Staying on current DashboardActivity.")

        }
    }

    private fun fetchUserById() {

        val user = databaseHelper.getUserById(userId)


        if (user != null) {
            userTextView.text = "Welcome, ${user.firstName}!"
        } else {
            Log.e("DashboardActivity", "User not found for user ID: $userId")
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupProfileNavigation() {

        val profileImage: ImageView = findViewById(R.id.Profile)
        profileImage.setOnClickListener {

            val intent = Intent(this, Profile::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
    }

}
