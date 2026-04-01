package com.secure.resident.drawer.facilities.di

import com.secure.resident.drawer.facilities.data.repository.ReservationRepositoryImpl
import com.secure.resident.drawer.facilities.domain.repository.ReservationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FacilitiesModule {

    @Binds
    @Singleton
    abstract fun bindFacilitiesRepository(
        impl : ReservationRepositoryImpl
    ) : ReservationRepository

}