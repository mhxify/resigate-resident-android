package com.secure.resident.guestrequest.di

import com.secure.resident.guestrequest.data.repository.RequestGuestRepositoryImpl
import com.secure.resident.guestrequest.domain.repository.RequestGuestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RequestGuestModule {

    @Binds
    @Singleton
    abstract fun bindsRequestGuestRepository(
        impl : RequestGuestRepositoryImpl
    ) : RequestGuestRepository
}