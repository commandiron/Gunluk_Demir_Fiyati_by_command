package com.example.gunluk_demir_fiyati_by_command

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gunluk_demir_fiyati_by_command.presentation.CheckListScreen
import com.example.gunluk_demir_fiyati_by_command.presentation.navigation.BottomNavItem
import com.example.gunluk_demir_fiyati_by_command.ui.theme.MainScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    isFabClicked: Boolean
) {
    AnimatedNavHost(navController, startDestination = BottomNavItem.Main.fullRoute) {
        //MAIN SCREEN
        composable(
            BottomNavItem.Main.fullRoute,
            arguments = listOf(
                navArgument("inputCheckList"){
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.CheckList.fullRoute ->slideIntoContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(2000))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.CheckList.fullRoute -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(2000))
                    else -> null
                }
            }) {

            val inputCheckList = remember{
                it.arguments?.getString("inputCheckList")
            }

            MainScreen(
                inputCheckList = inputCheckList ?: "",
                navController = navController,
                isFabClicked = isFabClicked
            )
        }

        //CHECKLIST SCREEN
        composable(BottomNavItem.CheckList.fullRoute,
            arguments = listOf(
                navArgument("inputDemirFiyatList"){
                    type = NavType.StringType
                    defaultValue = ""
                }, navArgument("inputCheckList"){
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = {
                when(initialState.destination.route){
                    BottomNavItem.Main.fullRoute -> slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(2000))
                    else -> null
                }

            }, exitTransition = {

                when (targetState.destination.route) {
                    BottomNavItem.Main.fullRoute -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(2000))
                    else -> null
                }
            }) {

            val inputDemirFiyatList = remember{
                it.arguments?.getString("inputDemirFiyatList")
            }
            val inputCheckList = remember{
                it.arguments?.getString("inputCheckList")
            }

            CheckListScreen(
                inputDemirFiyatList = inputDemirFiyatList ?: "",
                inputCheckList = inputCheckList ?: "",
                navController = navController)
        }
    }
}
