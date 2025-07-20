package com.example.practicelogin.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class User (
    @PrimaryKey(autoGenerate = true) val id : Int
)