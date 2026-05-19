package com.example.bois.data.repository

import com.example.bois.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor() : MainRepository {
    override suspend fun getGameData(): String {
        return "Game Data from Repository"
    }
}
