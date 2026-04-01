package com.secure.resident.drawer.incidents.di

import com.secure.resident.drawer.incidents.data.repository.IncidentRepositoryImpl
import com.secure.resident.drawer.incidents.domain.repository.IncidentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class IncidentModule {

    @Binds
    @Singleton
    abstract fun bindIncidentRepository(
        impl : IncidentRepositoryImpl
    ) : IncidentRepository
}