package com.example.gunluk_demir_fiyati_by_command.di

import android.content.Context
import androidx.room.Room
import com.example.gunluk_demir_fiyati_by_command.domain.repository.AppRepository
import com.example.gunluk_demir_fiyati_by_command.domain.use_case.GetData
import com.example.gunluk_demir_fiyati_by_command.domain.use_case.UseCases
import com.example.gunluk_demir_fiyati_by_command.data.repository.AppRepositoryImpl
import com.example.gunluk_demir_fiyati_by_command.domain.service.db.AppDao
import com.example.gunluk_demir_fiyati_by_command.domain.service.db.AppDatabase
import com.example.gunluk_demir_fiyati_by_command.domain.use_case.GetAllDataFromDb
import com.example.gunluk_demir_fiyati_by_command.domain.use_case.InsertDataToDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "appdatabase").build()

    @Singleton
    @Provides
    fun injectDao(database: AppDatabase) = database.appDao()

    @Provides
    fun provideRepository(
        appDao: AppDao
    ): AppRepository = AppRepositoryImpl(appDao)

    @Provides
    fun provideUseCases(repository: AppRepository) = UseCases(
        //Main Screen
        getData = GetData(repository),
        insertDataToDb = InsertDataToDb(repository),
        getAllDataFromDb = GetAllDataFromDb(repository)
    )
}