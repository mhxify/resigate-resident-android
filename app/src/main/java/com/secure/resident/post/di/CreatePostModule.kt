package com.secure.resident.post.di

import com.secure.resident.post.data.repository.CreatePostRepositoryImpl
import com.secure.resident.post.domain.repository.CreatePostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CreatePostModule {

    @Binds
    @Singleton
    abstract fun bindsPostRepository(
        impl : CreatePostRepositoryImpl
    ) : CreatePostRepository
}