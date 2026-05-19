package com.example.bois.di

import android.content.Context
import androidx.room.Room
import com.example.bois.data.source.local.AppDatabase
import com.example.bois.data.source.local.PlayerProgressionDao
import com.example.bois.data.source.local.ResourceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePlayerProgressionDao(db: AppDatabase): PlayerProgressionDao {
        return db.playerProgressionDao()
    }

    @Provides
    @Singleton
    fun provideResourceDao(db: AppDatabase): ResourceDao {
        return db.resourceDao()
    }
}
