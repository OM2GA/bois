package com.example.bois.domain.usecase

import com.example.bois.domain.repository.CompanyRepository
import javax.inject.Inject

class AddMoneyUseCase @Inject constructor(
    private val repository: CompanyRepository
) {
    suspend operator fun invoke(amount: Double) = repository.addMoney(amount)
}
