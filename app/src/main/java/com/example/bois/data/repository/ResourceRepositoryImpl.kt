package com.example.bois.data.repository

import com.example.bois.data.model.PlayerProgressionEntity
import com.example.bois.data.model.ResourceEntity
import com.example.bois.data.source.local.PlayerProgressionDao
import com.example.bois.data.source.local.ResourceDao
import com.example.bois.domain.model.Resource
import com.example.bois.domain.repository.ResourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceRepositoryImpl @Inject constructor(
    private val resourceDao: ResourceDao,
    private val playerProgressionDao: PlayerProgressionDao
) : ResourceRepository {

    private fun defaultResources() = listOf(
        Resource("Bois", 0.0, 1.0),
        Resource("Eau", 10.0, 0.0),
        Resource("Levure", 5.0, 0.0),
        Resource("Sucre", 5.0, 0.0),
        Resource("Céréales", 10.0, 0.0)
    )

    override fun getResources(): Flow<List<Resource>> {
        return resourceDao.getResources().map { entities ->
            if (entities.isEmpty()) defaultResources()
            else entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getCurrentResources(): List<Resource> = withContext(Dispatchers.IO) {
        val entities = resourceDao.getResourcesOnce()
        if (entities.isEmpty()) defaultResources()
        else entities.map { it.toDomainModel() }
    }

    override suspend fun updateResources(resources: List<Resource>) = withContext(Dispatchers.IO) {
        resourceDao.insertResources(resources.map { ResourceEntity.fromDomainModel(it) })
    }

    override suspend fun updateResource(name: String, amount: Double) = withContext(Dispatchers.IO) {
        // We need the current amount to add to it. 
        // Note: In a real app, we might want to do this in a transaction or use a SQL update with addition.
        val entities = resourceDao.getResourcesOnce()
        val entity = entities.find { it.name.lowercase() == name.lowercase() }
        if (entity != null) {
            resourceDao.updateResourceAmount(entity.name, entity.amount + amount)
        } else {
            // If it doesn't exist, we might need to create it from default if it's one of the defaults
            val default = defaultResources().find { it.name.lowercase() == name.lowercase() }
            if (default != null) {
                resourceDao.insertResource(ResourceEntity.fromDomainModel(default.copy(amount = default.amount + amount)))
            }
        }
    }

    override suspend fun getLastTickTime(): Long = withContext(Dispatchers.IO) {
        playerProgressionDao.getPlayerProgressionOnce()?.lastTickTime ?: System.currentTimeMillis()
    }

    override suspend fun saveLastTickTime(time: Long) = withContext(Dispatchers.IO) {
        val progression = playerProgressionDao.getPlayerProgressionOnce()
        if (progression != null) {
            playerProgressionDao.insertPlayerProgression(progression.copy(lastTickTime = time))
        } else {
            // This might happen on first run if resources tick before progression is saved.
            // We should probably ensure a progression exists.
        }
    }
}
