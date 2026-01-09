package com.example.briefly.ui


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.briefly.ui.screens.DetailScreen
import com.example.briefly.ui.screens.HomeScreen


@Composable
fun BrieflyNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // 1. Home Screen Route
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // 2. Detail Screen Route (Expecting a "title" argument)
        composable(
            route = Screen.Detail.route + "/{title}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = "No Title"
                    nullable = true
                }
            )
        ) {
            // OLD WAY: We had to get the title manually
            // val title = entry.arguments?.getString("title")
            // DetailScreen(navController = navController, title = title)

            // NEW WAY: Just call the screen. The ViewModel handles the title automatically!
            DetailScreen(navController = navController)
        }
    }
}