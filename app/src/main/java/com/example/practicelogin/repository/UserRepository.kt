package com.example.practicelogin.repository

import com.example.practicelogin.data.RoomUserLocalDataSource
import com.example.practicelogin.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val localDataSource: RoomUserLocalDataSource
) {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser : StateFlow<User?> = _currentUser.asStateFlow()

    suspend fun loginUser(email : String, password : String) : Result<User> {
        val user = localDataSource.authenticateUser(email, password)

        return if (user != null) {
            _currentUser.value = user
            Result.success(user)
        } else {
            Result.failure(Exception("Credenciales incorrectas"))
        }
    }

    suspend fun registerUser(user : User) : Result<String> {
        val userDb = localDataSource.getUserByEmail(user.email)

        return if (userDb != null) {
            Result.failure(Exception("El usuario ya existe"))
        } else {
            localDataSource.insertUser(user)
            Result.success("Usuario registrado")
        }
    }

    suspend fun updateUser(user: User): Result<String> {

        // Busca en la base de datos si ya existe un usuario con el mismo email.
        val userDb = localDataSource.getUserByEmail(user.email)

        // Si existe un usuario con ese email y su ID es diferente al que se quiere actualizar,
        // significa que ese email ya está siendo utilizado por otro usuario → error.
        return if (userDb != null && userDb.id != user.id) {
            Result.failure(Exception("El email ya está en uso por otro usuario"))
        } else {
            // Si no hay conflicto con el email, actualiza el usuario en la base de datos.
            localDataSource.updateUser(user)

            // Si el usuario actualizado es el mismo que está actualmente en sesión,
            // actualiza también la referencia interna del usuario actual.
            if (_currentUser.value?.id == user.id) {
                _currentUser.value = user
            }

            // Retorna éxito indicando que el usuario fue actualizado correctamente.
            Result.success("Usuario actualizado correctamente")
        }
    }

    fun getAllUsers() : Flow<List<User>> =localDataSource.getAllUsers()

    fun logout() { _currentUser.value = null }

    fun getCurrentUser() : User? { return _currentUser.value }

}