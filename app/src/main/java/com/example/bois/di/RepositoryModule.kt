package com.example.bois.di

import com.example.bois.data.repository.MainRepositoryImpl
import com.example.bois.data.repository.ResourceRepositoryImpl
import com.example.bois.domain.repository.MainRepository
import com.example.bois.domain.repository.ResourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository

    @Binds
    @Singleton
    abstract fun bindResourceRepository(
        resourceRepositoryImpl: ResourceRepositoryImpl
    ): ResourceRepository
}
