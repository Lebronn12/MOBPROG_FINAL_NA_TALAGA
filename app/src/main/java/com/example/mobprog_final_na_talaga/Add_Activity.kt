package com.example.mobprog_final_na_talaga

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class Add_Activity : AppCompatActivity() {

    private lateinit var beginnerButton: MaterialButton
    private lateinit var intermediateButton: MaterialButton
    private lateinit var advancedButton: MaterialButton
    private lateinit var expertButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)

        beginnerButton = findViewById(R.id.Beginner)
        intermediateButton = findViewById(R.id.intermediate)
        advancedButton = findViewById(R.id.Advanced)
        expertButton = findViewById(R.id.Expert)

        beginnerButton.setOnClickListener { onExpertiseSelected("Beginner") }
        intermediateButton.setOnClickListener { onExpertiseSelected("Intermediate") }
        advancedButton.setOnClickListener { onExpertiseSelected("Advanced") }
        expertButton.setOnClickListener { onExpertiseSelected("Expert") }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun onExpertiseSelected(expertise: String) {
        val intent = Intent(this, Muscle_group::class.java).apply {
            putExtra("EXPERTISE_LEVEL", expertise)
        }
        startActivity(intent)
    }


}

