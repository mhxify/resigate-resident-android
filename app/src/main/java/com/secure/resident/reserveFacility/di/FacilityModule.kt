package com.secure.resident.reserveFacility.di

import com.secure.resident.reserveFacility.data.repository.FacilityRepositoryImpl
import com.secure.resident.reserveFacility.domain.repository.FacilityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FacilityModule {

    @Binds
    @Singleton
    abstract fun bindReserveRepository(
        impl : FacilityRepositoryImpl
    ) : FacilityRepository
}