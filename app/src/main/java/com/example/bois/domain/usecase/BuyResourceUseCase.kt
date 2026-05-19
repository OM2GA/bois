package com.example.bois.domain.usecase

import com.example.bois.domain.repository.CompanyRepository
import com.example.bois.domain.repository.ResourceRepository
import javax.inject.Inject

class BuyResourceUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val companyRepository: CompanyRepository
) {
    private val prices = mapOf(
        "Eau" to 0.1,
        "Levure" to 2.0,
        "Sucre" to 1.0,
        "Céréales" to 0.5
    )

    suspend operator fun invoke(resourceName: String, amount: Double): Boolean {
        val pricePerUnit = prices[resourceName] ?: return false
        val totalPrice = pricePerUnit * amount
        
        val stats = companyRepository.getCurrentCompanyStats()
        if (stats.money >= totalPrice) {
            companyRepository.updateCompanyStats(stats.copy(money = stats.money - totalPrice))
            resourceRepository.updateResource(resourceName, amount)
            return true
        }
        
        return false
    }

    fun getPrice(resourceName: String): Double? = prices[resourceName]
}
