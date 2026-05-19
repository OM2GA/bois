package com.example.bois.domain.model

data class AlcoholType(
    val id: String,
    val name: String,
    val description: String,
    val basePrice: Double,
    val productionTimeSeconds: Long,
    val prerequisites: List<AlcoholPrerequisite> = emptyList()
)
