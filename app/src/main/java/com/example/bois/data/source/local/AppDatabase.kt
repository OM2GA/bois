package com.example.bois.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bois.data.model.PlayerProgressionEntity
import com.example.bois.data.model.ResourceEntity

@Database(
    entities = [
        PlayerProgressionEntity::class,
        ResourceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerProgressionDao(): PlayerProgressionDao
    abstract fun resourceDao(): ResourceDao

    companion object {
        const val DATABASE_NAME = "bois_db"
    }
}
