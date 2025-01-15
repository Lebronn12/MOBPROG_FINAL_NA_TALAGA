package com.example.mobprog_final_na_talaga

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*

class Payment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        val amountTextView = findViewById<TextView>(R.id.Amount)
        val cardNumberInput = findViewById<EditText>(R.id.CardNumber)
        val expiryDateInput = findViewById<EditText>(R.id.Expiry)
        val cvvInput = findViewById<EditText>(R.id.CVV)
        val confirmPaymentButton = findViewById<Button>(R.id.Confirm)
        val paymentStatus = findViewById<TextView>(R.id.paymentStatus)

        val amount = intent.getStringExtra("amount")
        amountTextView.text = "Amount to Pay: $amount"

        confirmPaymentButton.setOnClickListener {
            val cardNumber = cardNumberInput.text.toString()
            val expiryDate = expiryDateInput.text.toString()
            val cvv = cvvInput.text.toString()

            if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidCardNumber(cardNumber)) {
                Toast.makeText(this, "Invalid card number. Must be 16 digits.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidExpiryDate(expiryDate)) {
                Toast.makeText(this, "Invalid expiry date. Must be MM/YY and not in the past.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate CVV
            if (!isValidCVV(cvv)) {
                Toast.makeText(this, "Invalid CVV. Must be 3 digits.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            paymentStatus.text = "Payment of $amount successful!"
            Toast.makeText(this, "Payment Processed Successfully", Toast.LENGTH_LONG).show()

            setResult(Activity.RESULT_OK)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun isValidCardNumber(cardNumber: String): Boolean {
        return cardNumber.length == 16 && cardNumber.all { it.isDigit() }
    }

    private fun isValidExpiryDate(expiryDate: String): Boolean {
        val regex = """^(0[1-9]|1[0-2])\/([0-9]{2})$""".toRegex()
        if (!regex.matches(expiryDate)) {
            return false
        }

        val parts = expiryDate.split("/")
        val month = parts[0].toInt()
        val year = "20${parts[1]}".toInt()

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1

        return (year > currentYear) || (year == currentYear && month > currentMonth)
    }

    private fun isValidCVV(cvv: String): Boolean {
        return cvv.length == 3 && cvv.all { it.isDigit() }
    }
}
