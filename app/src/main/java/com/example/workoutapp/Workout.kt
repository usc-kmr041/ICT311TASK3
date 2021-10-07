package com.example.workoutapp

import java.util.*
import java.util.Date

data class Workout(val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isGroup: Boolean = false)