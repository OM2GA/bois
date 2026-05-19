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
        val boisAmount = sharedPreferences.getFloat("resource_bois_amount", 0.0f).toDouble()
        val boisProd = sharedPreferences.getFloat("resource_bois_prod", 1.0f).toDouble()
        return listOf(
            Resource("Bois", boisAmount, boisProd)
        )
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

    override suspend fun getLastTickTime(): Long = lastTickTime

    override suspend fun saveLastTickTime(time: Long) {
        lastTickTime = time
        sharedPreferences.edit().putLong("last_tick_time", time).apply()
    }
}
