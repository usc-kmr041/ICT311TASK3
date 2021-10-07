package com.example.workoutapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.workoutapp.Workout
import java.util.*

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout")
    fun getWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout WHERE id=(:id)")
    fun getWorkout(id: UUID): LiveData<Workout?>

}