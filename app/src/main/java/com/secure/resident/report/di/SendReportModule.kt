package com.secure.resident.report.di

import com.secure.resident.report.data.repository.SendReportRepositoryImpl
import com.secure.resident.report.domain.repository.SendReportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SendReportModule {

    @Binds
    @Singleton
    abstract fun bindSendReportRepo(
        impl : SendReportRepositoryImpl
    ) : SendReportRepository
}