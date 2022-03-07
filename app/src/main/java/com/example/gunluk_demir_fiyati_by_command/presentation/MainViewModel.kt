package com.example.gunluk_demir_fiyati_by_command.presentation

import androidx.lifecycle.ViewModel
import com.example.gunluk_demir_fiyati_by_command.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val useCases: UseCases
): ViewModel()  {
    val a = useCases.getData
}