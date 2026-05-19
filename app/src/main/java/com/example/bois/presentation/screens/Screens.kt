package com.example.bois.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DashboardScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Dashboard", style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun ProductionScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Production", style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun MarcheScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Marché", style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun AmeliorationsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Améliorations", style = MaterialTheme.typography.headlineLarge)
    }
}
