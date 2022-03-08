package com.example.gunluk_demir_fiyati_by_command.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.example.gunluk_demir_fiyati_by_command.domain.model.Response
import com.example.gunluk_demir_fiyati_by_command.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val useCases: UseCases
): ViewModel()  {

    var demirFiyatList = mutableStateOf<List<DemirFiyat>>(listOf())
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    init {
        getData()
    }

    fun getData(){
        viewModelScope.launch {
            useCases.getData.invoke().collect { response ->
                when(response){
                    is Response.Loading -> {
                        isRefreshing.value = true
                    }
                    is Response.Success -> {
                        delay(500)
                        isRefreshing.value = false
                        demirFiyatList.value = response.data
                    }
                    is Response.Error -> {}
                }
            }
        }
    }
}