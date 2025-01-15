package com.example.mobprog_final_na_talaga
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log



class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {
    companion object {
        private const val DATABASE_NAME = "fitness_app.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_WORKOUTS = "workouts_tbl"
        private const val TABLE_MUSCLES = "muscles_tbl"
        private const val TABLE_WORKOUT_MUSCLE_TARGET = "workoutmusclestarget_tbl"

    }
    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = """
            CREATE TABLE user_tbl (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                date_of_birth TEXT,
                gender TEXT,
                age INTEGER,
                height REAL,
                weight REAL,
                target_weight REAL,
                goal_id INTEGER,
                FOREIGN KEY (goal_id) REFERENCES goal_tbl(goal_id)
            )
        """.trimIndent()

        val createGoalTable = """
            CREATE TABLE goal_tbl (
                goal_id INTEGER PRIMARY KEY AUTOINCREMENT,
                goal_name TEXT NOT NULL
            )
        """.trimIndent()

        val createWorkoutsTable = """
            CREATE TABLE workouts_tbl (
                workout_id INTEGER PRIMARY KEY AUTOINCREMENT,
                workout_name TEXT NOT NULL,
                category TEXT,
                description TEXT,
                duration INTEGER
            )
        """.trimIndent()

        val createWorkoutSetsTable = """
            CREATE TABLE workoutsets_tbl (
                set_id INTEGER PRIMARY KEY AUTOINCREMENT,
                workout_id INTEGER,
                reps INTEGER,
                weight REAL,
                rest_time INTEGER,
                set_order INTEGER,
                FOREIGN KEY (workout_id) REFERENCES workouts_tbl(workout_id)
            )
        """.trimIndent()

        val createCustomWorkoutsTable = """
            CREATE TABLE customworkouts_tbl (
                custom_workout_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                workout_id INTEGER,
                date_created TEXT,
                date_performed TEXT,
                FOREIGN KEY (user_id) REFERENCES user_tbl(user_id),
                FOREIGN KEY (workout_id) REFERENCES workouts_tbl(workout_id)
            )
        """.trimIndent()

        val createActivityTrackingTable = """
            CREATE TABLE activitytracking_tbl (
                activity_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                custom_workout_id INTEGER,
                workout_id INTEGER,
                start_time TEXT,
                end_time TEXT,
                calories_burned REAL,
                FOREIGN KEY (user_id) REFERENCES user_tbl(user_id),
                FOREIGN KEY (custom_workout_id) REFERENCES customworkouts_tbl(custom_workout_id),
                FOREIGN KEY (workout_id) REFERENCES workouts_tbl(workout_id)
            )
        """.trimIndent()

        val createMusclesTable = """
            CREATE TABLE muscles_tbl (
                muscle_id INTEGER PRIMARY KEY AUTOINCREMENT,
                muscle_name TEXT NOT NULL
            )
        """.trimIndent()

        val createWorkoutMusclesTargetTable = """
            CREATE TABLE workoutmusclestarget_tbl (
                workout_id INTEGER,
                muscle_id INTEGER,
                PRIMARY KEY (workout_id, muscle_id),
                FOREIGN KEY (workout_id) REFERENCES workouts_tbl(workout_id),
                FOREIGN KEY (muscle_id) REFERENCES muscles_tbl(muscle_id)
            )
        """.trimIndent()

        db.execSQL(createUserTable)
        db.execSQL(createGoalTable)
        db.execSQL(createWorkoutsTable)
        db.execSQL(createWorkoutSetsTable)
        db.execSQL(createCustomWorkoutsTable)
        db.execSQL(createActivityTrackingTable)
        db.execSQL(createMusclesTable)
        db.execSQL(createWorkoutMusclesTargetTable)
        addDefaultWorkouts()
        insertDefaultData()
        addDefaultMuscleGroups()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS user_tbl")
        db.execSQL("DROP TABLE IF EXISTS goal_tbl")
        db.execSQL("DROP TABLE IF EXISTS workouts_tbl")
        db.execSQL("DROP TABLE IF EXISTS workoutsets_tbl")
        db.execSQL("DROP TABLE IF EXISTS customworkouts_tbl")
        db.execSQL("DROP TABLE IF EXISTS activitytracking_tbl")
        db.execSQL("DROP TABLE IF EXISTS muscles_tbl")
        db.execSQL("DROP TABLE IF EXISTS workoutmusclestarget_tbl")
        onCreate(db)
    }
    fun registerUser(firstName: String, lastName: String, email: String, username: String, password: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("first_name", firstName)
            put("last_name", lastName)
            put("email", email)
            put("username", username)
            put("password", password)
        }

        val result = db.insert("user_tbl", null, contentValues)
        if (result == -1L) {
        } else {
        }
    }
    fun getAllUsers(): List<User> {
        val userList = ArrayList<User>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user_tbl", null)

        if (cursor.moveToFirst()) {
            val usernameIndex = cursor.getColumnIndex("username")
            val passwordIndex = cursor.getColumnIndex("password")

            if (usernameIndex != -1 && passwordIndex != -1) {
                do {
                    val username = cursor.getString(usernameIndex)
                    val password = cursor.getString(passwordIndex)
                    Log.d("Database", "Username: $username, Password: $password")
                } while (cursor.moveToNext())
            } else {
                Log.e("Database", "Column not found in the table.")
            }
        }
        cursor.close()
        return userList
    }
    fun getUserById(userId: Int): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user_tbl WHERE user_id = ?", arrayOf(userId.toString()))

        return if (cursor.moveToFirst()) {
            val userIdIndex = cursor.getColumnIndex("user_id")
            val firstNameIndex = cursor.getColumnIndex("first_name")
            val lastNameIndex = cursor.getColumnIndex("last_name")
            val emailIndex = cursor.getColumnIndex("email")
            val usernameIndex = cursor.getColumnIndex("username")
            val passwordIndex = cursor.getColumnIndex("password")
            val dateOfBirthIndex = cursor.getColumnIndex("date_of_birth")
            val genderIndex = cursor.getColumnIndex("gender")
            val weightIndex = cursor.getColumnIndex("weight")
            val targetWeightIndex = cursor.getColumnIndex("target_weight")
            val goalIdIndex = cursor.getColumnIndex("goal_id")
            val heightIndex = cursor.getColumnIndex("height")
            val ageIndex = cursor.getColumnIndex("age")

            if (userIdIndex == -1 || firstNameIndex == -1 || lastNameIndex == -1 || emailIndex == -1 ||
                usernameIndex == -1 || passwordIndex == -1 || dateOfBirthIndex == -1 ||
                genderIndex == -1 || weightIndex == -1 || targetWeightIndex == -1 || goalIdIndex == -1 ||
                heightIndex == -1 || ageIndex == -1) {
                Log.e("DatabaseHelper", "Column index not found")
                return null
            }
            User(
                userId = cursor.getInt(userIdIndex),
                firstName = cursor.getString(firstNameIndex),
                lastName = cursor.getString(lastNameIndex),
                email = cursor.getString(emailIndex),
                username = cursor.getString(usernameIndex),
                password = cursor.getString(passwordIndex),
                dateOfBirth = cursor.getString(dateOfBirthIndex),
                gender = cursor.getString(genderIndex),
                weight = cursor.getFloat(weightIndex),
                targetWeight = cursor.getFloat(targetWeightIndex),
                goalId = cursor.getInt(goalIdIndex),
                height = cursor.getFloat(heightIndex),
                age = cursor.getInt(ageIndex)
            )
        } else {
            null
        }.also {
            cursor.close()
        }
    }
    fun loginUser(username: String, password: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user_tbl WHERE username = ? AND password = ?", arrayOf(username, password))

        return if (cursor.moveToFirst()) {
            val userIdIndex = cursor.getColumnIndex("user_id")
            val firstNameIndex = cursor.getColumnIndex("first_name")
            val lastNameIndex = cursor.getColumnIndex("last_name")
            val emailIndex = cursor.getColumnIndex("email")
            val usernameIndex = cursor.getColumnIndex("username")
            val passwordIndex = cursor.getColumnIndex("password")
            val dateOfBirthIndex = cursor.getColumnIndex("date_of_birth")
            val genderIndex = cursor.getColumnIndex("gender")
            val weightIndex = cursor.getColumnIndex("weight")
            val targetWeightIndex = cursor.getColumnIndex("target_weight")
            val goalIdIndex = cursor.getColumnIndex("goal_id")
            val heightIndex = cursor.getColumnIndex("height")
            val ageIndex = cursor.getColumnIndex("age")

            Log.d("LoginUser", "userIdIndex: $userIdIndex, firstNameIndex: $firstNameIndex, lastNameIndex: $lastNameIndex, emailIndex: $emailIndex, heightIndex: $heightIndex, ageIndex: $ageIndex")

            if (userIdIndex == -1 || firstNameIndex == -1 || lastNameIndex == -1 || emailIndex == -1 ||
                usernameIndex == -1 || passwordIndex == -1 || dateOfBirthIndex == -1 ||
                genderIndex == -1 || weightIndex == -1 || targetWeightIndex == -1 || goalIdIndex == -1 ||
                heightIndex == -1 || ageIndex == -1) {
                Log.e("LoginUser", "Column index not found")
                return null
            }

            User(
                userId = cursor.getInt(userIdIndex),
                firstName = cursor.getString(firstNameIndex),
                lastName = cursor.getString(lastNameIndex),
                email = cursor.getString(emailIndex),
                username = cursor.getString(usernameIndex),
                password = cursor.getString(passwordIndex),
                dateOfBirth = cursor.getString(dateOfBirthIndex),
                gender = cursor.getString(genderIndex),
                weight = cursor.getFloat(weightIndex),
                targetWeight = cursor.getFloat(targetWeightIndex),
                goalId = cursor.getInt(goalIdIndex),
                height = cursor.getFloat(heightIndex),
                age = cursor.getInt(ageIndex)
            )
        } else {
            null
        }.also {
            cursor.close()
        }
    }

    fun verifyUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user_tbl WHERE username = ? AND password = ?", arrayOf(username, password))

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close()
            return true
        }

        cursor.close()
        return false
    }

    fun updateGender(userId: Int, gender: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("gender", gender)

        db.update("user_tbl", values, "user_id = ?", arrayOf(userId.toString()))
    }
    fun getGoalIdByName(goalName: String): Int? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT goal_id FROM goal_tbl WHERE goal_name = ?", arrayOf(goalName))

        Log.d("DatabaseHelper", "Executing query: SELECT goal_id FROM goal_tbl WHERE goal_name = '$goalName'")

        return if (cursor.moveToFirst()) {

            val goalIdIndex = cursor.getColumnIndex("goal_id")
            if (goalIdIndex != -1) {
                val goalId = cursor.getInt(goalIdIndex)
                Log.d("DatabaseHelper", "Retrieved goal_id: $goalId")
                goalId
            } else {
                Log.e("DatabaseHelper", "Column 'goal_id' not found in the result")
                null
            }
        } else {
            Log.e("DatabaseHelper", "No matching goal found for name: $goalName")
            null
        }.also {
            cursor.close()
        }
    }

    fun updateMainGoal(userId: Int, goalId: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("goal_id", goalId)
        }
        db.update("user_tbl", contentValues, "user_id = ?", arrayOf(userId.toString()))
    }

    fun updateAge(userId: Int, age: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("age", age)
        db.update("user_tbl", values, "user_id = ?", arrayOf(userId.toString()))
        db.close()
    }

    fun updateHeight(userId: Int, height: Double) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("height", height)
        db.update("user_tbl", values, "user_id = ?", arrayOf(userId.toString()))
    }

    fun updateWeight(userId: Int, currentWeight: Double, targetWeight: Double) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("weight", currentWeight)
        values.put("target_weight", targetWeight)

        db.update("user_tbl", values, "user_id = ?", arrayOf(userId.toString()))
    }

    fun addGoal(goalName: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("goal_name", goalName)
        }

        val newRowId = db.insert("goal_tbl", null, values)
        if (newRowId == -1L) {
            Log.e("DatabaseHelper", "Failed to insert goal: $goalName")
        } else {
            Log.d("DatabaseHelper", "Successfully added goal: $goalName with ID: $newRowId")
        }

        return newRowId
    }

    fun getUserByUsername(username: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user_tbl WHERE username = ?", arrayOf(username))

        return if (cursor.moveToFirst()) {
            val userIdIndex = cursor.getColumnIndex("user_id")
            val firstNameIndex = cursor.getColumnIndex("first_name")
            val lastNameIndex = cursor.getColumnIndex("last_name")
            val emailIndex = cursor.getColumnIndex("email")
            val usernameIndex = cursor.getColumnIndex("username")
            val passwordIndex = cursor.getColumnIndex("password")
            val dateOfBirthIndex = cursor.getColumnIndex("date_of_birth")
            val genderIndex = cursor.getColumnIndex("gender")
            val weightIndex = cursor.getColumnIndex("weight")
            val targetWeightIndex = cursor.getColumnIndex("target_weight")
            val goalIdIndex = cursor.getColumnIndex("goal_id")
            val heightIndex = cursor.getColumnIndex("height")
            val ageIndex = cursor.getColumnIndex("age")

            if (userIdIndex == -1) Log.e("Database", "user_id column not found")
            if (firstNameIndex == -1) Log.e("Database", "first_name column not found")
            if (lastNameIndex == -1) Log.e("Database", "last_name column not found")
            if (emailIndex == -1) Log.e("Database", "email column not found")
            if (usernameIndex == -1) Log.e("Database", "username column not found")
            if (passwordIndex == -1) Log.e("Database", "password column not found")
            if (dateOfBirthIndex == -1) Log.e("Database", "date_of_birth column not found")
            if (genderIndex == -1) Log.e("Database", "gender column not found")
            if (weightIndex == -1) Log.e("Database", "weight column not found")
            if (targetWeightIndex == -1) Log.e("Database", "target_weight column not found")
            if (goalIdIndex == -1) Log.e("Database", "goal_id column not found")
            if (heightIndex == -1) Log.e("Database", "height column not found")
            if (ageIndex == -1) Log.e("Database", "age column not found")

            User(
                userId = if (userIdIndex != -1) cursor.getInt(userIdIndex) else -1,
                firstName = if (firstNameIndex != -1) cursor.getString(firstNameIndex) else "",
                lastName = if (lastNameIndex != -1) cursor.getString(lastNameIndex) else "",
                email = if (emailIndex != -1) cursor.getString(emailIndex) else "",
                username = if (usernameIndex != -1) cursor.getString(usernameIndex) else "",
                password = if (passwordIndex != -1) cursor.getString(passwordIndex) else "",
                dateOfBirth = if (dateOfBirthIndex != -1) cursor.getString(dateOfBirthIndex) else "",
                gender = if (genderIndex != -1) cursor.getString(genderIndex) else "",
                weight = if (weightIndex != -1) cursor.getFloat(weightIndex) else 0.0f,
                targetWeight = if (targetWeightIndex != -1) cursor.getFloat(targetWeightIndex) else 0.0f,
                goalId = if (goalIdIndex != -1) cursor.getInt(goalIdIndex) else -1,
                height = if (heightIndex != -1) cursor.getFloat(heightIndex) else 0.0f,
                age = if (ageIndex != -1) cursor.getInt(ageIndex) else -1
            )
        } else {
            null
        }.also {
            cursor.close()
        }
    }

    fun insertUser(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        dateOfBirth: String?,
        gender: String?,
        age: Int?,
        height: Float?,
        weight: Float?,
        targetWeight: Float?,
        goalId: Int?
    ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("first_name", firstName)
        contentValues.put("last_name", lastName)
        contentValues.put("email", email)
        contentValues.put("username", username)
        contentValues.put("password", password)
        contentValues.put("date_of_birth", dateOfBirth)
        contentValues.put("gender", gender)
        contentValues.put("age", age)
        contentValues.put("height", height)
        contentValues.put("weight", weight)
        contentValues.put("target_weight", targetWeight)
        contentValues.put("goal_id", goalId)

        return db.insert("user_tbl", null, contentValues)
    }

    fun insertActivity(userId: Int, customWorkoutId: Int?, workoutId: Int?, startTime: String, endTime: String, caloriesBurned: Float): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("user_id", userId)
            put("custom_workout_id", customWorkoutId)
            put("workout_id", workoutId)
            put("start_time", startTime)
            put("end_time", endTime)
            put("calories_burned", caloriesBurned)
        }

        return db.insert("activitytracking_tbl", null, contentValues)
    }
    fun getUserActivities(userId: Int): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM activitytracking_tbl WHERE user_id = ?", arrayOf(userId.toString()))
    }

    fun isUsernameExists(username: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM user_tbl WHERE username = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun addDefaultWorkouts() {
        val db = this.writableDatabase

        val muscle1Id = insertMuscle(db, "Chest")
        val muscle2Id = insertMuscle(db, "Back")
        val muscle3Id = insertMuscle(db, "Legs")
        val muscle4Id = insertMuscle(db, "Arms")

        insertWorkout(db, "Push-Up", "Beginner", "A basic upper body exercise.", 15, muscle1Id)
        insertWorkout(db, "Bench Press", "Intermediate", "A chest exercise using a barbell.", 30, muscle1Id)

        insertWorkout(db, "Pull-Up", "Beginner", "An upper body strength exercise.", 15, muscle2Id)
        insertWorkout(db, "Deadlift", "Intermediate", "A full-body exercise focusing on the back and legs.", 45, muscle2Id)

        insertWorkout(db, "Squat", "Beginner", "A lower body exercise that targets the legs.", 30, muscle3Id)
        insertWorkout(db, "Leg Press", "Intermediate", "A machine-based lower body exercise.", 30, muscle3Id)

        insertWorkout(db, "Bicep Curl", "Beginner", "An isolation exercise for the biceps.", 15, muscle4Id)
        insertWorkout(db, "Tricep Extension", "Intermediate", "An exercise to target the triceps.", 15, muscle4Id)

    }

    private fun insertMuscle(db: SQLiteDatabase, muscleName: String): Int {
        val values = ContentValues()
        values.put("muscle_name", muscleName)
        val muscleId = db.insert("muscles_tbl", null, values)

        return muscleId.toInt()
    }
    private fun insertWorkout(db: SQLiteDatabase, workoutName: String, category: String, description: String, duration: Int, muscleId: Int) {
        val values = ContentValues()
        values.put("workout_name", workoutName)
        values.put("category", category)
        values.put("description", description)
        values.put("duration", duration)

        val workoutId = db.insert("workouts_tbl", null, values)

        val workoutMuscleValues = ContentValues()
        workoutMuscleValues.put("workout_id", workoutId)
        workoutMuscleValues.put("muscle_id", muscleId)
        db.insert("workoutmusclestarget_tbl", null, workoutMuscleValues)
    }

    fun getMuscleGroupsForExpertise(expertiseLevel: String): List<String> {
        val muscleGroups = mutableListOf<String>()
        val db = this.readableDatabase

        val query = """
            SELECT DISTINCT m.muscle_name 
            FROM muscles_tbl m 
            JOIN workoutmusclestarget_tbl wmt ON m.muscle_id = wmt.muscle_id
            JOIN workouts_tbl w ON w.workout_id = wmt.workout_id
            WHERE w.category = ?
        """
        val cursor = db.rawQuery(query, arrayOf(expertiseLevel))

        if (cursor.moveToFirst()) {
            do {
                muscleGroups.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return muscleGroups
    }

    fun insertDefaultData() {
        val db = this.writableDatabase

        val muscles = listOf("Chest", "Back", "Legs", "Arms", "Shoulders")
        for (muscle in muscles) {
            val contentValues = ContentValues().apply {
                put("muscle_name", muscle)
            }
            db.insert("muscles_tbl", null, contentValues)
        }

        val workouts = listOf(
            "Bench Press" to "Beginner",
            "Deadlift" to "Intermediate",
            "Squats" to "Advanced"
        )
        for ((workoutName, category) in workouts) {
            val contentValues = ContentValues().apply {
                put("workout_name", workoutName)
                put("category", category)
                put("description", "Description for $workoutName")
                put("duration", 60)
            }
            val workoutId = db.insert("workouts_tbl", null, contentValues)

            when (workoutName) {
                "Bench Press" -> {
                    db.execSQL("INSERT INTO workoutmusclestarget_tbl (workout_id, muscle_id) VALUES (?, ?)", arrayOf(workoutId, 1))
                }
                "Deadlift" -> {
                    db.execSQL("INSERT INTO workoutmusclestarget_tbl (workout_id, muscle_id) VALUES (?, ?)", arrayOf(workoutId, 2))
                }
                "Squats" -> {
                    db.execSQL("INSERT INTO workoutmusclestarget_tbl (workout_id, muscle_id) VALUES (?, ?)", arrayOf(workoutId, 3))
                }
            }
        }

        db.close()
    }
    fun addDefaultMuscleGroups() {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO muscles_tbl (muscle_name, expertise_level) VALUES ('Chest', 'Beginner')")
        db.execSQL("INSERT INTO muscles_tbl (muscle_name, expertise_level) VALUES ('Back', 'Beginner')")
        db.execSQL("INSERT INTO muscles_tbl (muscle_name, expertise_level) VALUES ('Legs', 'Intermediate')")
        db.execSQL("INSERT INTO muscles_tbl (muscle_name, expertise_level) VALUES ('Shoulders', 'Intermediate')")
    }

    fun getAllMuscleGroups(): List<String> {
        val muscleGroups = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT muscle_name FROM muscles_tbl", null)

        if (cursor.moveToFirst()) {
            do {
                muscleGroups.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return muscleGroups
    }
    fun getWorkoutsForMuscleGroup(muscleGroup: String): List<String> {
        val workouts = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("""
            SELECT w.workout_name FROM workouts_tbl w
            INNER JOIN workoutmusclestarget_tbl wm ON w.workout_id = wm.workout_id
            INNER JOIN muscles_tbl m ON wm.muscle_id = m.muscle_id
            WHERE m.muscle_name = ?
        """, arrayOf(muscleGroup))

        if (cursor.moveToFirst()) {
            do {
                workouts.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return workouts
    }
    fun getMuscleIdByName(muscleName: String): Int? {
        val db = this.readableDatabase
        val cursor = db.query(
            "muscles_tbl",
            arrayOf("muscle_id"),
            "muscle_name = ?",
            arrayOf(muscleName),
            null,
            null,
            null
        )

        Log.d("DatabaseQuery", "Querying muscle_id for: $muscleName")

        return if (cursor.moveToFirst()) {

            val index = cursor.getColumnIndex("muscle_id")
            if (index != -1) {
                cursor.getInt(index)
            } else {
                Log.e("DatabaseQuery", "Column 'muscle_id' not found")
                null
            }
        } else {
            Log.e("DatabaseQuery", "No results found for muscle name: $muscleName")
            null
        }.also {
            cursor.close()
        }
    }

}
