package com.example.bois.domain.usecase

import com.example.bois.domain.repository.MainRepository

class GetGameDataUseCase(private val repository: MainRepository) {
    suspend operator fun invoke(): String {
        return repository.getGameData()
    }
}
