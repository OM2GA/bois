package com.example.bois.domain.usecase

import com.example.bois.domain.repository.MainRepository
import javax.inject.Inject

class GetGameDataUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(): String {
        return repository.getGameData()
    }
}
