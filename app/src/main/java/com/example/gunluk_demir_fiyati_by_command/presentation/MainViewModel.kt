package com.example.gunluk_demir_fiyati_by_command.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gunluk_demir_fiyati_by_command.domain.model.CityCheck
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

    var maskedDemirFiyatList = mutableStateOf<List<DemirFiyat>>(listOf())
        private set

    var demirFiyatList = mutableStateOf<List<DemirFiyat>>(listOf())
        private set

    var checkList = mutableStateOf<List<CityCheck>>(listOf())
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    init {
        getDataFromJsoup()
    }

    fun getDataFromJsoup(){
        viewModelScope.launch {
            useCases.getData.invoke().collect { response ->
                when(response){
                    is Response.Loading -> {
                        isRefreshing.value = true
                    }
                    is Response.Success -> {
                        demirFiyatList.value = response.data
                        getCheckListFromDb()
                        delay(500)
                        isRefreshing.value = false
                    }
                    is Response.Error -> {
                        isRefreshing.value = false
                    }
                }
            }
        }
    }

    fun insertCheckListToDb(cityCheckList: List<CityCheck>){
        viewModelScope.launch {
            useCases.insertCheckListToDb.invoke(cityCheckList).collect { response ->
                when(response){
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        getCheckListFromDb()
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun getCheckListFromDb(){
        viewModelScope.launch {
            useCases.getCheckListFromDb.invoke().collect { response ->
                when(response){
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        checkList.value = response.data
                        maskedDemirFiyatList.value = listOf()
                        checkList.value.forEach { check->
                            if(check.isChecked){
                                val checkedFiyat = demirFiyatList.value.find { it.id == check.id }
                                if(checkedFiyat != null){
                                    maskedDemirFiyatList.value = maskedDemirFiyatList.value + checkedFiyat
                                }
                            }
                        }
                    }
                    is Response.Error -> {}
                }
            }
        }
    }
}