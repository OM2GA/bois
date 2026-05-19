package com.example.bois.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bois.data.model.PlayerProgressionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerProgressionDao {
    @Query("SELECT * FROM player_progression WHERE id = 0")
    fun getPlayerProgression(): Flow<PlayerProgressionEntity?>

    @Query("SELECT * FROM player_progression WHERE id = 0")
    fun getPlayerProgressionOnce(): PlayerProgressionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayerProgression(progression: PlayerProgressionEntity)

    @Query("DELETE FROM player_progression")
    fun deletePlayerProgression()
}
