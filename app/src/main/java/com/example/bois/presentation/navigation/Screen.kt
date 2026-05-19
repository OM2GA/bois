package com.example.bois.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Production : Screen("production")
    object Marche : Screen("marche")
    object Ameliorations : Screen("ameliorations")
}
