package com.example.bois.data.repository

import com.example.bois.data.source.local.PlayerProgressionDao
import com.example.bois.domain.model.GameState
import com.example.bois.domain.repository.CompanyRepository
import com.example.bois.domain.repository.GameRepository
import com.example.bois.domain.repository.ResourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val companyRepository: CompanyRepository,
    private val resourceRepository: ResourceRepository,
    private val playerProgressionDao: PlayerProgressionDao
) : GameRepository {

    override fun getGameState(): Flow<GameState> {
        return combine(
            companyRepository.getCompanyStats(),
            resourceRepository.getResources()
        ) { stats, resources ->
            GameState(stats, resources)
        }
    }

    override suspend fun saveLastTickTime(time: Long) = withContext(Dispatchers.IO) {
        val progression = playerProgressionDao.getPlayerProgressionOnce()
        if (progression != null) {
            playerProgressionDao.insertPlayerProgression(progression.copy(lastTickTime = time))
        }
    }

    override suspend fun getLastTickTime(): Long = withContext(Dispatchers.IO) {
        playerProgressionDao.getPlayerProgressionOnce()?.lastTickTime ?: System.currentTimeMillis()
    }
}
