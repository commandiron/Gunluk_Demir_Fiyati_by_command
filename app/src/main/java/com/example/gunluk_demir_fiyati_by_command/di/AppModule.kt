package com.example.gunluk_demir_fiyati_by_command.di

import com.example.gunluk_demir_fiyati_by_command.domain.repository.AppRepository
import com.example.gunluk_demir_fiyati_by_command.domain.use_case.GetData
import com.example.gunluk_demir_fiyati_by_command.domain.use_case.UseCases
import com.example.gunluk_demir_fiyati_by_command.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRepository(
    ): AppRepository = AppRepositoryImpl()

    @Provides
    fun provideUseCases(repository: AppRepository) = UseCases(
        //Main Screen
        getData = GetData(repository),
    )
}