package com.example.bois.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bois.domain.model.Resource

@Entity(tableName = "resources")
data class ResourceEntity(
    @PrimaryKey val name: String,
    val amount: Double,
    val productionPerSecond: Double
) {
    fun toDomainModel(): Resource {
        return Resource(
            name = name,
            amount = amount,
            productionPerSecond = productionPerSecond
        )
    }

    companion object {
        fun fromDomainModel(resource: Resource): ResourceEntity {
            return ResourceEntity(
                name = resource.name,
                amount = resource.amount,
                productionPerSecond = resource.productionPerSecond
            )
        }
    }
}
