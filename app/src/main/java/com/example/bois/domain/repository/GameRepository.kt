package com.example.bois.domain.repository

import com.example.bois.domain.model.GameState
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGameState(): Flow<GameState>
    suspend fun saveLastTickTime(time: Long)
    suspend fun getLastTickTime(): Long
}
