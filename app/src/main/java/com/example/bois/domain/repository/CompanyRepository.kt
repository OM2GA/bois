package com.example.bois.domain.repository

import com.example.bois.domain.model.CompanyStats
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    fun getCompanyStats(): Flow<CompanyStats>
    suspend fun getCurrentCompanyStats(): CompanyStats
    suspend fun updateCompanyStats(stats: CompanyStats)
    suspend fun addMoney(amount: Double)
    suspend fun addExperience(amount: Double)
    suspend fun addReputation(amount: Double)
}
