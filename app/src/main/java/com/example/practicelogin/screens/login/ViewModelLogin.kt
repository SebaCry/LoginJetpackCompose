package com.example.practicelogin.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicelogin.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())

    val uiState : StateFlow<LoginState> = _uiState.asStateFlow()

    fun onEmailChange(email : String) {
        _uiState.value =_uiState.value.copy(
            email = email,
            errorMessage = null
        )
    }

    fun onPasswordChange(password : String) {
        _uiState.value =_uiState.value.copy(
            password = password,
            errorMessage = null
        )
    }

    fun login() {
        val currentState = _uiState.value

        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.value = currentState.copy(
                errorMessage = "No puede haber campos vacios",
                isLoading =false
            )
            return
        }

        _uiState.value =currentState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {

            val result =userRepository.loginUser(currentState.email, currentState.password)

            result.fold(
                onSuccess = { user ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        loginSuccess = user,
                        errorMessage = null
                    )
                },
                onFailure = { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = error.message,
                        loginSuccess = null
                    )
                }
            )

        }
    }
}