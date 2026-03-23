package com.secure.resident.main.di

import com.secure.resident.main.data.remote.websocket.ChatSocketManagerImpl
import com.secure.resident.main.domain.repository.ChatSocketManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SocketModule {

    @Binds
    @Singleton
    abstract fun bindChatSocketManager(
        impl: ChatSocketManagerImpl
    ): ChatSocketManager
}