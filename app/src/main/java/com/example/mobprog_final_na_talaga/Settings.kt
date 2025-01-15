package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.app.AlertDialog
import android.view.View
import android.widget.TextView

class Settings : AppCompatActivity() {

    private var isSubscribed = false
    private lateinit var subscribeButton: MaterialButton
    private lateinit var cancelSubscriptionButton: MaterialButton
    private lateinit var subscribeText: TextView
    private val PAYMENT_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        subscribeButton = findViewById(R.id.Subscribe)
        cancelSubscriptionButton = findViewById(R.id.CancelSubscription)
        subscribeText = findViewById(R.id.SubscribeText)
        val logoutButton: Button = findViewById(R.id.Logout)

        cancelSubscriptionButton.setOnClickListener {
            showCancelConfirmationDialog()
        }

        logoutButton.setOnClickListener {
            logOut()
        }

        val showContact: MaterialButton = findViewById(R.id.ContactUs)

        showContact.setOnClickListener{
            ShowContactDialog()
        }

        val fixedAmount = "₱1,500"
        subscribeButton.setOnClickListener{
            val intent = Intent(this, Payment::class.java)
            intent.putExtra("amount", fixedAmount)
            startActivityForResult(intent, PAYMENT_REQUEST_CODE)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PAYMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                handleSubscription()
            } else {
            }
        }
    }

    private fun logOut() {
        val sharedPref = getSharedPreferences("userSession", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    private fun ShowContactDialog() {
        val contactDetails = """           
            Contact no.: 09123456789
            
            Email: PeakFit@gmail.com
            
            Social Media: PeakFit
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Contact Details")
            .setMessage(contactDetails)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun handleSubscription() {
        isSubscribed = true

        subscribeText.text = "You are currently subscribed"
        subscribeButton.visibility = View.GONE
        cancelSubscriptionButton.visibility = View.VISIBLE
    }

    private fun showCancelConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cancel Subscription")
            .setMessage("Are you sure you want to cancel your subscription?")
            .setPositiveButton("Yes") { _, _ ->
                handleCancelSubscription()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun handleCancelSubscription() {
        isSubscribed = false

        subscribeText.text = "You are not currently subscribed"
        subscribeButton.visibility = View.VISIBLE
        cancelSubscriptionButton.visibility = View.GONE

        Toast.makeText(this, "Subscription canceled", Toast.LENGTH_SHORT).show()
    }
}
