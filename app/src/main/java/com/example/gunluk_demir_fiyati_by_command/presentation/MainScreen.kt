package com.example.gunluk_demir_fiyati_by_command.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.gunluk_demir_fiyati_by_command.presentation.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController){

    mainViewModel.a

    Text(text = "GÜNLÜK DEMİR FİYATI")

}