package com.example.mobprog_final_na_talaga

import android.database.Cursor

data class User(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String,
    val dateOfBirth: String,
    val gender: String,
    val age: Int,
    val height: Float,
    val weight: Float,
    val targetWeight: Float,
    val goalId: Int?
){
    constructor(cursor: Cursor) : this(
        userId = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
        firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name")),
        lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name")),
        email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
        username = cursor.getString(cursor.getColumnIndexOrThrow("username")),
        password = cursor.getString(cursor.getColumnIndexOrThrow("password")),
        dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow("date_of_birth")),
        gender = cursor.getString(cursor.getColumnIndexOrThrow("gender")),
        age = cursor.getInt(cursor.getColumnIndexOrThrow("age")),
        height = cursor.getFloat(cursor.getColumnIndexOrThrow("height")),
        weight = cursor.getFloat(cursor.getColumnIndexOrThrow("weight")),
        targetWeight = cursor.getFloat(cursor.getColumnIndexOrThrow("target_weight")),
        goalId = cursor.getInt(cursor.getColumnIndexOrThrow("goal_id"))
    )
}