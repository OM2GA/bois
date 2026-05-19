package com.example.bois.domain.usecase

import com.example.bois.domain.model.Resource
import com.example.bois.domain.repository.ResourceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ResourceTickerUseCase @Inject constructor(
    private val repository: ResourceRepository
) {
    operator fun invoke(intervalMs: Long = 1000L): Flow<List<Resource>> = flow {
        val lastTime = repository.getLastTickTime()
        val currentTime = System.currentTimeMillis()
        val elapsedSeconds = (currentTime - lastTime) / 1000.0
        
        if (elapsedSeconds > 0) {
            val currentResources = repository.getCurrentResources()
            val updatedResources = currentResources.map { resource ->
                resource.copy(amount = resource.amount + (resource.productionPerSecond * elapsedSeconds))
            }
            repository.updateResources(updatedResources)
            repository.saveLastTickTime(currentTime)
            emit(updatedResources)
        }

        var nextTickTime = System.currentTimeMillis()
        
        while (true) {
            delay(intervalMs)
            val tickTime = System.currentTimeMillis()
            val tickElapsedSeconds = (tickTime - nextTickTime) / 1000.0
            
            val currentResources = repository.getCurrentResources()
            val updatedResources = currentResources.map { resource ->
                resource.copy(amount = resource.amount + (resource.productionPerSecond * tickElapsedSeconds))
            }
            
            repository.updateResources(updatedResources)
            repository.saveLastTickTime(tickTime)
            emit(updatedResources)
            nextTickTime = tickTime
        }
    }
}
