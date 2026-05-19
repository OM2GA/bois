package com.example.bois.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bois.data.model.ResourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourceDao {
    @Query("SELECT * FROM resources")
    fun getResources(): Flow<List<ResourceEntity>>

    @Query("SELECT * FROM resources")
    fun getResourcesOnce(): List<ResourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResources(resources: List<ResourceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResource(resource: ResourceEntity)

    @Query("UPDATE resources SET amount = :amount WHERE name = :name")
    fun updateResourceAmount(name: String, amount: Double)

    @Query("DELETE FROM resources")
    fun deleteResources()
}
