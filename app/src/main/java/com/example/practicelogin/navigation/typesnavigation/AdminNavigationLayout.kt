package com.example.practicelogin.navigation.typesnavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.practicelogin.navigation.sidecomponents.SideNavigation
import com.example.practicelogin.screens.admin.dashboard.AdminScreen
import com.example.practicelogin.screens.admin.users.AddUserScreen
import com.example.practicelogin.screens.admin.users.AdminUsersScreen
import com.example.practicelogin.screens.admin.users.EditUserScreen
import com.example.practicelogin.screens.client.ClientProfileScreen
import com.example.practicelogin.screens.client.ClientScreen

@Composable
fun AdminNavigationLayout(
    viewModel : ViewModelNavigation
) {

    val navController = rememberNavController()
    val navigationItems by viewModel.navigationItems.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var isDrawerOpen by remember { mutableStateOf(false) }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AdminNavigation(navController = navController)

        FloatingActionButton(
            onClick = { isDrawerOpen = !isDrawerOpen },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Barra lateral"
            )
        }

        if (isDrawerOpen) {
            val overlayAlpha by animateFloatAsState(
                targetValue = if (isDrawerOpen) 0.6f else 0f,
                animationSpec = tween(300),
                label = "overlay_alpha"
            )

            Box (
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = overlayAlpha))
                    .clickable { isDrawerOpen = false }
            ) {

                AnimatedVisibility(
                    visible = isDrawerOpen,
                    enter = slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(300)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(300)
                    )
                ) {
                    Surface (
                        modifier = Modifier
                            .width(280.dp)
                            .fillMaxHeight(),
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 8.dp
                    ) {
                        SideNavigation(
                            items = navigationItems,
                            currentRoute = currentRoute,
                            onItemClick = { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                isDrawerOpen = false
                            },
                            onLogout = {
                                viewModel.logout()
                                isDrawerOpen = false
                            },
                            text = "Panel de administrador"
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun AdminNavigation(navController : NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "admin_dashboard"
    ) {
        composable("admin_dashboard") {
            AdminScreen()
        }

        composable("admin_users") {
            AdminUsersScreen(navController = navController)
        }

        composable("add_user") {
            AddUserScreen(navController = navController)
        }

        composable("edit_user/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            EditUserScreen(
                userId = userId,
                navController = navController
            )

        }
    }
}