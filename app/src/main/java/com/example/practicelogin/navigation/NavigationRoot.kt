package com.example.practicelogin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.practicelogin.data.UserRole
import com.example.practicelogin.navigation.typesnavigation.AdminNavigationLayout
import com.example.practicelogin.navigation.typesnavigation.ClientNavigationLayout
import com.example.practicelogin.navigation.typesnavigation.ViewModelNavigation

@Composable
fun NavigationRoot() {
    val viewModel : ViewModelNavigation = hiltViewModel()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    when {
        currentUser == null -> {
            AuthNavigation()
        }

        currentUser?.role == UserRole.CLIENT -> {
            ClientNavigationLayout(viewModel)
        }

        currentUser?.role == UserRole.ADMIN -> {
            AdminNavigationLayout(viewModel)
        }

    }
}