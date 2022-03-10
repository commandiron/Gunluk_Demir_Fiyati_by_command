package com.example.gunluk_demir_fiyati_by_command.domain.use_case

import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.example.gunluk_demir_fiyati_by_command.domain.repository.AppRepository

class GetCheckListFromDb(
    private val repository: AppRepository
) {
    suspend operator fun invoke() = repository.getCheckListFromDb()
}