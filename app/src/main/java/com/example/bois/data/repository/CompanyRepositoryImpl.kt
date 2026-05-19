package com.example.bois.data.repository

import android.content.Context
import com.example.bois.domain.model.CompanyStats
import com.example.bois.domain.repository.CompanyRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow

@Singleton
class CompanyRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CompanyRepository {

    private val sharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

    private val _stats = MutableStateFlow(loadStats())

    private fun loadStats(): CompanyStats {
        val money = sharedPreferences.getFloat("company_money", 100.0f).toDouble()
        val reputation = sharedPreferences.getFloat("company_reputation", 0.0f).toDouble()
        val level = sharedPreferences.getInt("company_level", 1)
        val experience = sharedPreferences.getFloat("company_experience", 0.0f).toDouble()
        
        return CompanyStats(
            money = money,
            reputation = reputation,
            level = level,
            experience = experience,
            experienceToNextLevel = calculateExperienceToNextLevel(level)
        )
    }

    private fun calculateExperienceToNextLevel(level: Int): Double {
        return 100.0 * 1.5.pow(level - 1)
    }

    override fun getCompanyStats(): Flow<CompanyStats> = _stats.asStateFlow()

    override suspend fun getCurrentCompanyStats(): CompanyStats = _stats.value

    override suspend fun updateCompanyStats(stats: CompanyStats) {
        _stats.value = stats
        saveStats(stats)
    }

    override suspend fun addMoney(amount: Double) {
        val current = _stats.value
        val updated = current.copy(money = current.money + amount)
        updateCompanyStats(updated)
    }

    override suspend fun addExperience(amount: Double) {
        val current = _stats.value
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
        val current = _stats.value
        val updated = current.copy(reputation = current.reputation + amount)
        updateCompanyStats(updated)
    }

    private fun saveStats(stats: CompanyStats) {
        with(sharedPreferences.edit()) {
            putFloat("company_money", stats.money.toFloat())
            putFloat("company_reputation", stats.reputation.toFloat())
            putInt("company_level", stats.level)
            putFloat("company_experience", stats.experience.toFloat())
            apply()
        }
    }
}
