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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.unit.dp
import com.example.bois.presentation.marche.MarcheViewModel

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
fun MarcheScreen(viewModel: MarcheViewModel = hiltViewModel()) {
    val resources by viewModel.resources.collectAsState()
    val stats by viewModel.companyStats.collectAsState()
    
    val materialsToBuy = listOf("Eau", "Levure", "Sucre", "Céréales")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Marché des Matières Premières", style = MaterialTheme.typography.headlineMedium)
        
        stats?.let {
            Text(text = "Argent disponible : ${String.format("%.2f", it.money)} €", style = MaterialTheme.typography.titleMedium)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn {
            items(materialsToBuy) { materialName ->
                val resource = resources.find { it.name == materialName }
                val price = viewModel.getPrice(materialName)
                
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = materialName, style = MaterialTheme.typography.titleLarge)
                            Text(text = "En stock : ${String.format("%.2f", resource?.amount ?: 0.0)}")
                            Text(text = "Prix : ${String.format("%.2f", price)} € / unité")
                        }
                        
                        Row {
                            Button(onClick = { viewModel.buyResource(materialName, 1.0) }) {
                                Text("+1")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { viewModel.buyResource(materialName, 10.0) }) {
                                Text("+10")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AmeliorationsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Améliorations", style = MaterialTheme.typography.headlineLarge)
    }
}
