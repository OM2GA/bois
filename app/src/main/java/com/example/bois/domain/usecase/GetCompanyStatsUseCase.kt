package com.example.bois.domain.usecase

import com.example.bois.domain.model.CompanyStats
import com.example.bois.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompanyStatsUseCase @Inject constructor(
    private val repository: CompanyRepository
) {
    operator fun invoke(): Flow<CompanyStats> = repository.getCompanyStats()
}
