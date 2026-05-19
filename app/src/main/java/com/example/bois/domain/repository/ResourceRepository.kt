package com.example.bois.domain.repository

import com.example.bois.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ResourceRepository {
    fun getResources(): Flow<List<Resource>>
    suspend fun getCurrentResources(): List<Resource>
    suspend fun updateResources(resources: List<Resource>)
    suspend fun getLastTickTime(): Long
    suspend fun saveLastTickTime(time: Long)
}
