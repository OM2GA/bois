package com.example.bois.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bois.domain.model.CompanyStats

@Entity(tableName = "player_progression")
data class PlayerProgressionEntity(
    @PrimaryKey val id: Int = 0, // We only store one player progression
    val money: Double,
    val reputation: Double,
    val level: Int,
    val experience: Double,
    val experienceToNextLevel: Double,
    val lastTickTime: Long = System.currentTimeMillis()
) {
    fun toDomainModel(): CompanyStats {
        return CompanyStats(
            money = money,
            reputation = reputation,
            level = level,
            experience = experience,
            experienceToNextLevel = experienceToNextLevel
        )
    }

    companion object {
        fun fromDomainModel(stats: CompanyStats, lastTickTime: Long? = null): PlayerProgressionEntity {
            return PlayerProgressionEntity(
                money = stats.money,
                reputation = stats.reputation,
                level = stats.level,
                experience = stats.experience,
                experienceToNextLevel = stats.experienceToNextLevel,
                lastTickTime = lastTickTime ?: System.currentTimeMillis()
            )
        }
    }
}
