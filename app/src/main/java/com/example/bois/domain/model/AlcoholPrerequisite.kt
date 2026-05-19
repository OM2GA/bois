package com.example.bois.domain.model

sealed class AlcoholPrerequisite {
    data class ResourceRequired(
        val resourceName: String,
        val amount: Double
    ) : AlcoholPrerequisite()

    data class LevelRequired(
        val level: Int
    ) : AlcoholPrerequisite()

    data class AlcoholUnlocked(
        val alcoholId: String
    ) : AlcoholPrerequisite()
}
