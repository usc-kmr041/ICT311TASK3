package com.example.workoutapp

import android.app.Application

class WorkoutAppApplication : Application(){

    override fun onCreate(){
        super.onCreate()
        WorkoutRepository.initialize(this)
    }
}