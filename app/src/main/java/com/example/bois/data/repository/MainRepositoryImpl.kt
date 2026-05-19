package com.example.bois.data.repository

import com.example.bois.domain.repository.MainRepository

class MainRepositoryImpl : MainRepository {
    override suspend fun getGameData(): String {
        return "Game Data from Repository"
    }
}
