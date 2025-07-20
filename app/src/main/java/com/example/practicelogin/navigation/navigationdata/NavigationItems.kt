package com.example.practicelogin.navigation.navigationdata

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person

object NavigationItems {

    val clientItems = listOf(
        NavigationItem("Inicio", Icons.Default.Home, "client_home"),
        NavigationItem("Perfil", Icons.Default.Person, "client_profile")
    )

    val adminItems = listOf(
        NavigationItem("Dashboard", Icons.Default.Dashboard, "admin_dashbaord"),
        NavigationItem("Usuarios", Icons.Default.People, "admin_users")
    )


}