package com.secure.resident.main.di

import com.secure.resident.main.data.repository.MainRepositoryImpl
import com.secure.resident.main.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        impl : MainRepositoryImpl
    ) : MainRepository

}