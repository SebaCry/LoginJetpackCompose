package com.example.practicelogin.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RoomUserLocalDataSource @Inject constructor(
    private val userDao : UserDao
) {
    suspend fun insertUser(user : User) {
        return userDao.insertUser(user)
    }

    suspend fun deleteUser(user : User) {
        return userDao.deleteUser(user)
    }

    suspend fun updateUser(user: User) {
        return userDao.updateUser(user)
    }

    suspend fun authenticateUser(email : String, password : String) :User? {
        return userDao.getUserByCredentials(email, password)
    }

    suspend fun getUserByEmail(email : String) : User? {
        return userDao.getUserByEmail(email)
    }

    fun getAllUsers() : Flow<List<User>> {
        return userDao.getAllUsers()
    }

}