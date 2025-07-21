package com.example.practicelogin.navigation.typesnavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicelogin.data.UserRole
import com.example.practicelogin.navigation.navigationdata.NavigationItems
import com.example.practicelogin.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ViewModelNavigation @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    val currentUser= userRepository.currentUser

    val navigationItems = currentUser.map{ user ->
        when (user?.role) {
            UserRole.CLIENT ->
                NavigationItems.clientItems
            UserRole.ADMIN ->
                NavigationItems.adminItems
            else -> emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun logout() {
        userRepository.logout()
    }





}