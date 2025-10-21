package com.example.flyx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flyx.ui.screens.HomeScreen
import com.example.flyx.ui.screens.SplashScreen
import com.example.flyx.ui.screens.auth.LoginScreen
import com.example.flyx.ui.screens.auth.SignUpScreen
import com.example.flyx.ui.screens.EventDetailScreen
import com.example.flyx.ui.screens.ClubDetailScreen
import com.example.flyx.ui.screens.SearchScreen
import com.example.flyx.ui.screens.ProfileScreen
import com.example.flyx.ui.viewmodel.EventViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FlyxNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "splash"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        
        // Authentication Screens
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate("signup")
                }
            )
        }
        
        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("home") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
        
        // Main App Screens
        composable("home") {
            val viewModel: EventViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onEventClick = { eventId ->
                    navController.navigate("event_detail/$eventId")
                },
                onClubClick = { clubId ->
                    navController.navigate("club_detail/$clubId")
                },
                onSearchClick = {
                    navController.navigate("search")
                },
                onProfileClick = {
                    navController.navigate("profile")
                }
            )
        }
        
        composable("search") {
            val viewModel: EventViewModel = hiltViewModel()
            SearchScreen(
                viewModel = viewModel,
                onEventClick = { eventId ->
                    navController.navigate("event_detail/$eventId")
                },
                onClubClick = { clubId ->
                    navController.navigate("club_detail/$clubId")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("event_detail/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            val viewModel: EventViewModel = hiltViewModel()
            EventDetailScreen(
                eventId = eventId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onClubClick = { clubId ->
                    navController.navigate("club_detail/$clubId")
                }
            )
        }
        
        composable("club_detail/{clubId}") { backStackEntry ->
            val clubId = backStackEntry.arguments?.getString("clubId") ?: ""
            val viewModel: EventViewModel = hiltViewModel()
            ClubDetailScreen(
                clubId = clubId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onEventClick = { eventId ->
                    navController.navigate("event_detail/$eventId")
                }
            )
        }
        
        composable("profile") {
            val viewModel: EventViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}