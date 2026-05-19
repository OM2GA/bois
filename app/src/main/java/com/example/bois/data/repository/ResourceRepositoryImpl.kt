package com.example.bois.data.repository

import android.content.Context
import com.example.bois.domain.model.Resource
import com.example.bois.domain.repository.ResourceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceRepository {
    
    private val sharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

    private val _resources = MutableStateFlow(loadResources())
    
    private var lastTickTime: Long = sharedPreferences.getLong("last_tick_time", System.currentTimeMillis())

    private fun loadResources(): List<Resource> {
        val resources = mutableListOf<Resource>()
        
        val resourceConfigs = listOf(
            Triple("Bois", 0.0f, 1.0f),
            Triple("Eau", 10.0f, 0.0f),
            Triple("Levure", 5.0f, 0.0f),
            Triple("Sucre", 5.0f, 0.0f),
            Triple("Céréales", 10.0f, 0.0f)
        )

        resourceConfigs.forEach { (name, defaultAmount, defaultProd) ->
            val amount = sharedPreferences.getFloat("resource_${name.lowercase()}_amount", defaultAmount).toDouble()
            val prod = sharedPreferences.getFloat("resource_${name.lowercase()}_prod", defaultProd).toDouble()
            resources.add(Resource(name, amount, prod))
        }
        
        return resources
    }

    override fun getResources(): Flow<List<Resource>> = _resources.asStateFlow()

    override suspend fun getCurrentResources(): List<Resource> = _resources.value

    override suspend fun updateResources(resources: List<Resource>) {
        _resources.value = resources
        // Persist
        with(sharedPreferences.edit()) {
            resources.forEach { resource ->
                putFloat("resource_${resource.name.lowercase()}_amount", resource.amount.toFloat())
                putFloat("resource_${resource.name.lowercase()}_prod", resource.productionPerSecond.toFloat())
            }
            apply()
        }
    }

    override suspend fun updateResource(name: String, amount: Double) {
        val currentResources = _resources.value.toMutableList()
        val index = currentResources.indexOfFirst { it.name.lowercase() == name.lowercase() }
        if (index != -1) {
            val resource = currentResources[index]
            val updatedResource = resource.copy(amount = resource.amount + amount)
            currentResources[index] = updatedResource
            _resources.value = currentResources
            
            sharedPreferences.edit()
                .putFloat("resource_${name.lowercase()}_amount", updatedResource.amount.toFloat())
                .apply()
        }
    }

    override suspend fun getLastTickTime(): Long = lastTickTime

    override suspend fun saveLastTickTime(time: Long) {
        lastTickTime = time
        sharedPreferences.edit().putLong("last_tick_time", time).apply()
    }
}
