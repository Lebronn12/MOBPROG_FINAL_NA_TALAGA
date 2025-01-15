package com.example.mobprog_final_na_talaga

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CollectionActivity : AppCompatActivity() {

    private val workouts = mapOf(
        "Chest" to listOf("Push-Ups", "Bench Press", "Chest Fly", "Incline Press", "Cable Crossover"),
        "Back" to listOf("Pull-Ups", "Bent-Over Rows", "Deadlifts", "Lat Pulldown", "T-Bar Row"),
        "Biceps" to listOf("Bicep Curls", "Hammer Curls", "Concentration Curls", "Preacher Curls", "Cable Curls"),
        "Legs" to listOf("Squats", "Lunges", "Deadlifts", "Leg Press", "Leg Curl")
    )

    private lateinit var spinner: Spinner
    private lateinit var applyFilterButton: Button
    private lateinit var listBiceps: ListView
    private lateinit var listBack: ListView
    private lateinit var listChest: ListView
    private lateinit var listLegs: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_collection)

        spinner = findViewById(R.id.spinner)
        applyFilterButton = findViewById(R.id.applyFilterButton)
        listBiceps = findViewById(R.id.listBiceps)
        listBack = findViewById(R.id.listBack)
        listChest = findViewById(R.id.listChest)
        listLegs = findViewById(R.id.listLegs)

        displayWorkouts("All")

        applyFilterButton.setOnClickListener {
            val selectedGroup = spinner.selectedItem as String
            displayWorkouts(selectedGroup)
        }

        val muscleGroups = listOf("All", "Chest", "Back", "Biceps", "Legs")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, muscleGroups)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun displayWorkouts(muscleGroup: String) {
        if (muscleGroup == "All" || muscleGroup == "Biceps") {
            setListAdapter(listBiceps, workouts["Biceps"])
        } else {
            listBiceps.adapter = null
        }

        if (muscleGroup == "All" || muscleGroup == "Back") {
            setListAdapter(listBack, workouts["Back"])
        } else {
            listBack.adapter = null
        }

        if (muscleGroup == "All" || muscleGroup == "Chest") {
            setListAdapter(listChest, workouts["Chest"])
        } else {
            listChest.adapter = null
        }

        if (muscleGroup == "All" || muscleGroup == "Legs") {
            setListAdapter(listLegs, workouts["Legs"])
        } else {
            listLegs.adapter = null
        }
    }

    private fun setListAdapter(listView: ListView, items: List<String>?) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items ?: emptyList())
        listView.adapter = adapter
    }
}
