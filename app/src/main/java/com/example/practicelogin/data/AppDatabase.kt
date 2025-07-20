package com.example.practicelogin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [User::class], version = 1)
@TypeConverters
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
}

class UserConverterType {

    @TypeConverter
    fun fromUserToRole(role : UserRole) : String {
        return role.name
    }

    @TypeConverter
    fun toUserRole(role : String) : UserRole {
        return UserRole.valueOf(role)
    }
}