package com.example.practicelogin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUserByCredentials(email : String, password : String) : User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email : String) : User?

    @Query("SELECT * FROM users")
    fun getAllUsers() : Flow<List<User>>

    @Insert
    suspend fun insertUser(user : User)

    @Update
    suspend fun updateUser(user : User)

    @Delete
    suspend fun deleteUser(user : User)


}