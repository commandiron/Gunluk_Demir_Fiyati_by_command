package com.example.gunluk_demir_fiyati_by_command.data.repository


import com.example.gunluk_demir_fiyati_by_command.domain.repository.AppRepository
import com.example.gunluk_demir_fiyati_by_command.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(): AppRepository {
    override fun getData(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
    }
}