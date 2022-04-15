package com.example.gunluk_demir_fiyati_by_command

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gunluk_demir_fiyati_by_command.presentation.CustomBottomAppBar
import com.example.gunluk_demir_fiyati_by_command.presentation.CustomFloatingActionButton
import com.example.gunluk_demir_fiyati_by_command.presentation.navigation.BottomNavItem
import com.example.gunluk_demir_fiyati_by_command.ui.theme.Gunluk_Demir_Fiyati_by_commandTheme
import com.example.gunluk_demir_fiyati_by_command.ui.theme.onbackGround
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //For Insets library
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Gunluk_Demir_Fiyati_by_commandTheme {
                ProvideWindowInsets() {
                    MainScreenView()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenView() {

    val snackbarHostState = remember {SnackbarHostState()}
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    val bottomBarState = rememberSaveable {(mutableStateOf(false))}
    val fabState = rememberSaveable {(mutableStateOf(false))}

    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var isFabClicked by remember { mutableStateOf(false)}

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = onbackGround,
        darkIcons = false
    )

    Scaffold(
        modifier = Modifier.padding(rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyTop = true,
            applyBottom = true,
        )),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            fabState.value =
                currentRoute != BottomNavItem.CheckList.fullRoute
            CustomFloatingActionButton(
                fabState = fabState.value,
                onClick = {
                    isFabClicked = !isFabClicked
                },
                backgroundColor = Color(0xffFFA000)
            ) {
                Icon(
                    Icons.Filled.Settings,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            bottomBarState.value =
                currentRoute != BottomNavItem.CheckList.fullRoute

            CustomBottomAppBar(bottomBarState = bottomBarState.value)}

    ) {
        NavigationGraph(navController, isFabClicked)
    }

}
