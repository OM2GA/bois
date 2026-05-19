package com.example.bois.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bois.presentation.main.MainViewModel

@Composable
fun DashboardScreen(viewModel: MainViewModel = hiltViewModel()) {
    val resources by viewModel.resources.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Tableau de Bord", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            resources.forEach { resource ->
                Text(
                    text = "${resource.name} : ${String.format("%.2f", resource.amount)}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "(+${resource.productionPerSecond}/s)",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
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
