package com.example.bois.domain.repository

interface MainRepository {
    suspend fun getGameData(): String
}
