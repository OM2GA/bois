package com.example.bois.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bois.presentation.screens.AmeliorationsScreen
import com.example.bois.presentation.screens.DashboardScreen
import com.example.bois.presentation.screens.MarcheScreen
import com.example.bois.presentation.screens.ProductionScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(route = Screen.Dashboard.route) {
            DashboardScreen()
        }
        composable(route = Screen.Production.route) {
            ProductionScreen()
        }
        composable(route = Screen.Marche.route) {
            MarcheScreen()
        }
        composable(route = Screen.Ameliorations.route) {
            AmeliorationsScreen()
        }
    }
}
