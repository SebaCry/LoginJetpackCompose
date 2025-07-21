package com.example.practicelogin.screens.login

import com.example.practicelogin.data.User

data class LoginState(
    val email : String = "",
    val password : String = "",
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val loginSuccess : User? = null
)