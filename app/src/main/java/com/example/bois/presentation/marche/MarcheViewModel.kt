package com.example.bois.presentation.marche

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bois.domain.usecase.BuyResourceUseCase
import com.example.bois.domain.repository.ResourceRepository
import com.example.bois.domain.repository.CompanyRepository
import com.example.bois.domain.model.Resource
import com.example.bois.domain.model.CompanyStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarcheViewModel @Inject constructor(
    private val buyResourceUseCase: BuyResourceUseCase,
    resourceRepository: ResourceRepository,
    companyRepository: CompanyRepository
) : ViewModel() {

    val resources: StateFlow<List<Resource>> = resourceRepository.getResources()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val companyStats: StateFlow<CompanyStats?> = companyRepository.getCompanyStats()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun buyResource(name: String, amount: Double) {
        viewModelScope.launch {
            buyResourceUseCase(name, amount)
        }
    }

    fun getPrice(name: String): Double {
        return buyResourceUseCase.getPrice(name) ?: 0.0
    }
}
