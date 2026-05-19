package com.example.bois.data.repository

import com.example.bois.domain.model.AlcoholPrerequisite
import com.example.bois.domain.model.AlcoholType
import com.example.bois.domain.repository.AlcoholRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlcoholRepositoryImpl @Inject constructor() : AlcoholRepository {

    private val _alcoholTypes = MutableStateFlow(listOf(
        AlcoholType(
            id = "beer",
            name = "Bière",
            description = "Une boisson fermentée rafraîchissante.",
            basePrice = 5.0,
            productionTimeSeconds = 30,
            prerequisites = listOf(
                AlcoholPrerequisite.LevelRequired(1)
            )
        ),
        AlcoholType(
            id = "wine",
            name = "Vin",
            description = "Un classique intemporel issu de la vigne.",
            basePrice = 12.0,
            productionTimeSeconds = 60,
            prerequisites = listOf(
                AlcoholPrerequisite.LevelRequired(2)
            )
        ),
        AlcoholType(
            id = "cider",
            name = "Cidre",
            description = "Le doux parfum de la pomme fermentée.",
            basePrice = 7.0,
            productionTimeSeconds = 45,
            prerequisites = listOf(
                AlcoholPrerequisite.LevelRequired(1)
            )
        ),
        AlcoholType(
            id = "brandy",
            name = "Eau de vie",
            description = "Un distillat puissant pour les plus braves.",
            basePrice = 25.0,
            productionTimeSeconds = 120,
            prerequisites = listOf(
                AlcoholPrerequisite.LevelRequired(5),
                AlcoholPrerequisite.AlcoholUnlocked("wine")
            )
        )
    ))

    override fun getAlcoholTypes(): Flow<List<AlcoholType>> = _alcoholTypes.asStateFlow()

    override suspend fun getAlcoholType(id: String): AlcoholType? {
        return _alcoholTypes.value.find { it.id == id }
    }
}
