package com.example.gunluk_demir_fiyati_by_command.domain.use_case

import com.example.gunluk_demir_fiyati_by_command.domain.repository.AppRepository

class GetData(
    private val repository: AppRepository
) {
    operator fun invoke() = repository.getData()
}