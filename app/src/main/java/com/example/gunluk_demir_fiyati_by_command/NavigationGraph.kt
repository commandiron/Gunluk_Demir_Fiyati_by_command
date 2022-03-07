package com.example.gunluk_demir_fiyati_by_command

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gunluk_demir_fiyati_by_command.ui.theme.MainScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavItem.Main.fullRoute) {
        //MAIN SCREEN
        composable(BottomNavItem.Main.fullRoute) {
            MainScreen(
                navController = navController
            )
        }
    }
}
