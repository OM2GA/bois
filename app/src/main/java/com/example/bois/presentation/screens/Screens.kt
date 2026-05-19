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
    val companyStats by viewModel.companyStats.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Tableau de Bord", style = MaterialTheme.typography.headlineLarge)
            
            companyStats?.let { stats ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Entreprise", style = MaterialTheme.typography.titleLarge)
                Text(text = "Niveau : ${stats.level}")
                Text(text = "Argent : ${String.format("%.2f", stats.money)} €")
                Text(text = "Réputation : ${String.format("%.1f", stats.reputation)}")
                Text(
                    text = "Expérience : ${String.format("%.0f", stats.experience)} / ${String.format("%.0f", stats.experienceToNextLevel)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Ressources", style = MaterialTheme.typography.titleLarge)
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
