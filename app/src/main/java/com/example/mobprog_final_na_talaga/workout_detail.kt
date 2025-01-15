package com.example.mobprog_final_na_talaga

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class workout_detail : AppCompatActivity() {
    private lateinit var muscleNameTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var exercise1: TextView
    private lateinit var exercise2: TextView
    private lateinit var exercise3: TextView
    private lateinit var exercise4: TextView
    private lateinit var exercise1Details: TextView
    private lateinit var exercise2Details: TextView
    private lateinit var exercise3Details: TextView
    private lateinit var exercise4Details: TextView
    private lateinit var muscleGroupTextView: TextView
    private lateinit var startButton: MaterialButton
    private lateinit var stopButton: MaterialButton
    private var timer: CountDownTimer? = null
    private var totalTime: Long = 60000
    private var isTimerRunning = false
    private lateinit var startTimer: MaterialButton
    private lateinit var durationEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workout_detail)

        startButton = findViewById(R.id.start)
        stopButton = findViewById(R.id.stop)
        startTimer = findViewById(R.id.startTimer)
        timerTextView = findViewById(R.id.timerTextView)
        durationEditText = findViewById(R.id.durationEditText)

        startTimer.setOnClickListener {
            if (!isTimerRunning) {
                val inputTime = durationEditText.text.toString()
                if (inputTime.isNotEmpty()) {
                    totalTime = inputTime.toLong() * 1000
                    startTimer(totalTime)
                }
            }
        }

            startButton.setOnClickListener {
                Toast.makeText(this, "Workout Started!", Toast.LENGTH_SHORT).show()
            }
            stopButton.setOnClickListener {
                if (isTimerRunning) {
                    timer?.cancel()
                    isTimerRunning = false
                    timerTextView.text = "00:00"
                    Toast.makeText(this, "Workout Stopped!", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Workout Stopped!", Toast.LENGTH_SHORT).show()
            }

            muscleNameTextView = findViewById(R.id.muscleNameTextView)
            timerTextView = findViewById(R.id.timerTextView)
            exercise1 = findViewById(R.id.exercise1)
            exercise2 = findViewById(R.id.exercise2)
            exercise3 = findViewById(R.id.exercise3)
            exercise4 = findViewById(R.id.exercise4)
            exercise1Details = findViewById(R.id.exercise1Details)
            exercise2Details = findViewById(R.id.exercise2Details)
            exercise3Details = findViewById(R.id.exercise3Details)
            exercise4Details = findViewById(R.id.exercise4Details)

            val intent = intent
            val muscleGroup = intent.getStringExtra("MUSCLE_GROUP")
            muscleNameTextView.text = muscleGroup

            setExercises(muscleGroup)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

    }
    private fun setExercises(muscleGroup: String?) {
        when (muscleGroup) {
            "Legs" -> {
                exercise1.text = "Squats"
                exercise1Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
                exercise2.text = "Lunges"
                exercise2Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
                exercise3.text = "Leg Press"
                exercise3Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
                exercise4.text = "Deadlifts"
                exercise4Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
            }
            "Biceps" -> {
                exercise1.text = "Bicep Curls"
                exercise1Details.text = "Sets: 3 | Reps: 12 | Rest: 60s"
                exercise2.text = "Hammer Curls"
                exercise2Details.text = "Sets: 3 | Reps: 12 | Rest: 60s"
                exercise3.text = "Chin-Ups"
                exercise3Details.text = "Sets: 3 | Reps: 8 | Rest: 90s"
                exercise4.text = "Concentration Curls"
                exercise4Details.text = "Sets: 3 | Reps: 12 | Rest: 60s"
            }
            "Back" -> {
                exercise1.text = "Pull-Ups"
                exercise1Details.text = "Sets: 3 | Reps: 8 | Rest: 90s"
                exercise2.text = "Bent-Over Rows"
                exercise2Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
                exercise3.text = "Deadlifts"
                exercise3Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
                exercise4.text = "Lat Pulldowns"
                exercise4Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
            }
            "Chest" -> {
                exercise1.text = "Bench Press"
                exercise1Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
                exercise2.text = "Push-Ups"
                exercise2Details.text = "Sets: 3 | Reps: 12 | Rest: 60s"
                exercise3.text = "Dumbbell Flyes"
                exercise3Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
                exercise4.text = "Chest Press"
                exercise4Details.text = "Sets: 3 | Reps: 10 | Rest: 60s"
            }
        }
    }
    private fun startTimer(timeInMillis: Long) {
        isTimerRunning = true
        timer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                timerTextView.text = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)
            }

            override fun onFinish() {
                isTimerRunning = false
                timerTextView.text = "00:00"
            }
        }.start()
    }
}