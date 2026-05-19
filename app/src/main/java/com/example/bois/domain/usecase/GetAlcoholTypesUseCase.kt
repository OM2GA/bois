package com.example.bois.domain.usecase

import com.example.bois.domain.model.AlcoholType
import com.example.bois.domain.repository.AlcoholRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlcoholTypesUseCase @Inject constructor(
    private val alcoholRepository: AlcoholRepository
) {
    operator fun invoke(): Flow<List<AlcoholType>> {
        return alcoholRepository.getAlcoholTypes()
    }
}
