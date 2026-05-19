package com.example.bois.domain.usecase

import com.example.bois.domain.model.GameState
import com.example.bois.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameStateUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(): Flow<GameState> {
        return repository.getGameState()
    }
}
