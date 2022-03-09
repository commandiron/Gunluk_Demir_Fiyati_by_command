package com.example.gunluk_demir_fiyati_by_command.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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

    var demirFiyatListFromDb = mutableStateOf<List<DemirFiyat>>(listOf())
        private set

    var demirFiyatListFromJsoup = mutableStateOf<MutableList<DemirFiyat>>(arrayListOf())
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    init {
        getDataFromJsoup()
        getAllDataFromDb()
    }

    fun getDataFromJsoup(){
        viewModelScope.launch {
            useCases.getData.invoke().collect { response ->
                when(response){
                    is Response.Loading -> {
                        isRefreshing.value = true
                    }
                    is Response.Success -> {
                        delay(500)
                        isRefreshing.value = false
                        demirFiyatListFromJsoup.value = response.data.toMutableList()
                    }
                    is Response.Error -> {
                        println("error" + response.message)
                        isRefreshing.value = false
                    }
                }
            }
        }
    }

    fun insertDataWithChackList(demirFiyatListChecked: List<DemirFiyat>){

        demirFiyatListFromJsoup.value = demirFiyatListChecked.toMutableList()

        demirFiyatListChecked.forEach { demirFiyat ->
            demirFiyatListChecked.forEach {  demirFiyatChecked ->
                if(demirFiyatChecked.bolge == demirFiyat.bolge){
                    if(demirFiyatChecked.userCheck != null){
                        if(demirFiyatChecked.userCheck!!){
                            insertDataToDb(demirFiyat)
                        }
                    }
                }
            }
        }
    }

    private fun insertDataToDb(demirFiyat: DemirFiyat){
        viewModelScope.launch {
            useCases.insertDataToDb.invoke(demirFiyat).collect { response ->
                when(response){
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        getAllDataFromDb()
                    }
                    is Response.Error -> {}
                }
            }
        }
    }

    fun getAllDataFromDb(){
        viewModelScope.launch {
            useCases.getAllDataFromDb.invoke().collect { response ->
                when(response){
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        demirFiyatListFromDb.value = response.data
                    }
                    is Response.Error -> {}
                }
            }
        }
    }
}