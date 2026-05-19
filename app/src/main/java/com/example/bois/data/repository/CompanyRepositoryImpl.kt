package com.example.bois.data.repository

import com.example.bois.data.model.PlayerProgressionEntity
import com.example.bois.data.source.local.PlayerProgressionDao
import com.example.bois.domain.model.CompanyStats
import com.example.bois.domain.repository.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow

@Singleton
class CompanyRepositoryImpl @Inject constructor(
    private val playerProgressionDao: PlayerProgressionDao
) : CompanyRepository {

    private fun calculateExperienceToNextLevel(level: Int): Double {
        return 100.0 * 1.5.pow(level - 1)
    }

    private fun defaultStats() = CompanyStats(
        money = 100.0,
        reputation = 0.0,
        level = 1,
        experience = 0.0,
        experienceToNextLevel = calculateExperienceToNextLevel(1)
    )

    override fun getCompanyStats(): Flow<CompanyStats> {
        return playerProgressionDao.getPlayerProgression().map { entity ->
            entity?.toDomainModel() ?: defaultStats()
        }
    }

    override suspend fun getCurrentCompanyStats(): CompanyStats = withContext(Dispatchers.IO) {
        playerProgressionDao.getPlayerProgressionOnce()?.toDomainModel() ?: defaultStats()
    }

    override suspend fun updateCompanyStats(stats: CompanyStats) = withContext(Dispatchers.IO) {
        val currentEntity = playerProgressionDao.getPlayerProgressionOnce()
        playerProgressionDao.insertPlayerProgression(
            PlayerProgressionEntity.fromDomainModel(stats, currentEntity?.lastTickTime)
        )
    }

    override suspend fun addMoney(amount: Double) {
        val current = getCurrentCompanyStats()
        val updated = current.copy(money = current.money + amount)
        updateCompanyStats(updated)
    }

    override suspend fun addExperience(amount: Double) {
        val current = getCurrentCompanyStats()
        var newExperience = current.experience + amount
        var newLevel = current.level
        var newExpToNext = current.experienceToNextLevel

        while (newExperience >= newExpToNext) {
            newExperience -= newExpToNext
            newLevel++
            newExpToNext = calculateExperienceToNextLevel(newLevel)
        }

        val updated = current.copy(
            level = newLevel,
            experience = newExperience,
            experienceToNextLevel = newExpToNext
        )
        updateCompanyStats(updated)
    }

    override suspend fun addReputation(amount: Double) {
        val current = getCurrentCompanyStats()
        val updated = current.copy(reputation = current.reputation + amount)
        updateCompanyStats(updated)
    }
}
