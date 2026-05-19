package com.example.bois.domain.repository

import com.example.bois.domain.model.AlcoholType
import kotlinx.coroutines.flow.Flow

interface AlcoholRepository {
    fun getAlcoholTypes(): Flow<List<AlcoholType>>
    suspend fun getAlcoholType(id: String): AlcoholType?
}
