package com.example.gunluk_demir_fiyati_by_command.domain.repository

import androidx.lifecycle.LiveData
import com.example.gunluk_demir_fiyati_by_command.domain.model.CityCheck
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.example.gunluk_demir_fiyati_by_command.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getData(): Flow<Response<List<DemirFiyat>>>

    suspend fun insertCheckListToDb(cityCheckList: List<CityCheck>): Flow<Response<Boolean>>
    suspend fun getCheckListFromDb(): Flow<Response<List<CityCheck>>>

}