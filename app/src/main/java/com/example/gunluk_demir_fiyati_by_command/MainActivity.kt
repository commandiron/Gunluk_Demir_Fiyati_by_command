package com.example.gunluk_demir_fiyati_by_command

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.gunluk_demir_fiyati_by_command.ui.theme.Gunluk_Demir_Fiyati_by_commandTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //MainViewModel
        mainViewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)

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

@Composable
fun MainScreenView() {

    val snackbarHostState = remember {SnackbarHostState()}
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.padding(rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyTop = true,
            applyBottom = true,
        )),
        scaffoldState = scaffoldState

    ) {
        NavigationGraph(navController)
    }

}
