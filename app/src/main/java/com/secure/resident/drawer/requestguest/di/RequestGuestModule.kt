package com.secure.resident.drawer.requestguest.di

import com.secure.resident.drawer.requestguest.data.repository.RequestGuestRepositoryImpl
import com.secure.resident.drawer.requestguest.domain.repository.RequestGuestRepository
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
    abstract fun bindRequestGuestRepo(
        impl : RequestGuestRepositoryImpl
    ) : RequestGuestRepository
}