package com.secure.resident.drawer.reports.di

import com.secure.resident.drawer.reports.data.repository.ReportsRepositoryImpl
import com.secure.resident.drawer.reports.domain.repository.ReportsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReportModule {

    @Binds
    @Singleton
    abstract fun bindReportRepository(
        impl : ReportsRepositoryImpl
    ) : ReportsRepository

}