package com.example.workoutapp

import androidx.lifecycle.ViewModel

class WorkoutListViewModel : ViewModel() {

    val workouts = mutableListOf<Workout>()
    init{
        for (i in 0 until 100){
            val workout = Workout()
            workout.title = "Workout #$i"
            workout.isGroup = i % 2 == 0
            workouts += workout
        }
    }
}