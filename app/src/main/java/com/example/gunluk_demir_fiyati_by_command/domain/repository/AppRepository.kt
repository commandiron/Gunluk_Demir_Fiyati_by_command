package com.example.gunluk_demir_fiyati_by_command.domain.repository

import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.example.gunluk_demir_fiyati_by_command.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    //Login Screen
    fun getData(): Flow<Response<List<DemirFiyat>>>
}