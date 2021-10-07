package com.example.workoutapp.database

import android.content.Context
import androidx.room.*
import com.example.workoutapp.Workout

@Database(entities = [Workout::class],version=1, exportSchema = false)
@TypeConverters(WorkoutTypeConverters::class)
abstract class WorkoutDatabase:RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    companion object {

        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        fun getInstance(context: Context): WorkoutDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WorkoutDatabase::class.java,
                        "workout-database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}