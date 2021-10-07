package com.example.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import java.util.Date

@Entity
data class Workout(@PrimaryKey val id: UUID = UUID.randomUUID(),
                   var title: String = "",
                   var location: String = "",
                   var date: Date = Date(),
                   var isGroup: Boolean = false)