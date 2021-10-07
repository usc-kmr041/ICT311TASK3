package com.example.workoutapp.database

import android.content.Context
import androidx.room.*
import com.example.workoutapp.Workout

@Database(entities = [Workout::class],version=1, exportSchema = false)
@TypeConverters(WorkoutTypeConverters::class)
abstract class WorkoutDatabase:RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

}