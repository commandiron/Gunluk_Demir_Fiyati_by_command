package com.example.gunluk_demir_fiyati_by_command.ui.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.gunluk_demir_fiyati_by_command.presentation.MainViewModel
import com.example.gunluk_demir_fiyati_by_command.R
import com.example.gunluk_demir_fiyati_by_command.domain.model.CityCheck
import com.example.gunluk_demir_fiyati_by_command.presentation.CustomAlertDialog
import com.example.gunluk_demir_fiyati_by_command.presentation.CustomHorizontalPager
import com.example.gunluk_demir_fiyati_by_command.presentation.CustomText
import com.example.gunluk_demir_fiyati_by_command.presentation.navigation.BottomNavItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainScreen(
    inputCheckList: String,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
    isFabClicked: Boolean) {

    //Alttaki + yukarı doğru giderken -ye dönecek ve alt kısmına yerleşecek top barın bu animasyonu yapmaya çalışıcam.
    //Üzerine tıklayınca büyüyüp daha ayrıntılı bir grafik konulabilir.

    //Sağa sola kayacak şekilde şehirlerdeki fiyatları göstereceğim.

    //Progress Indicator
    val isRefreshing by remember { mainViewModel.isRefreshing }

    //Two List For CheckList
    val demirFiyatList = mainViewModel.demirFiyatList.value
    val checkList = mainViewModel.checkList.value


    LaunchedEffect(key1 = Unit){
        if(inputCheckList.isNotEmpty()){
            val checkListFromGson: List<CityCheck> = Gson().fromJson(inputCheckList, object : TypeToken<List<CityCheck>>() {}.type)
            mainViewModel.insertCheckListToDb(checkListFromGson)
        }
    }

    //Fab Button Clicked
    var flag by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = isFabClicked){
        if(flag){
            flag = false
        }else{
            if(demirFiyatList.isNotEmpty() && checkList.isNotEmpty()){
                navController.popBackStack()
                navController.navigate(BottomNavItem.CheckList.screen_route + "?inputDemirFiyatList=${Gson().toJson(demirFiyatList)}" + "?inputCheckList=${Gson().toJson(checkList)}")
            }else if(demirFiyatList.isNotEmpty() && checkList.isEmpty()){
                navController.popBackStack()
                navController.navigate(BottomNavItem.CheckList.screen_route + "?inputDemirFiyatList=${Gson().toJson(demirFiyatList)}" + "?inputCheckList=")
            }else if(demirFiyatList.isEmpty() && checkList.isNotEmpty()){
                navController.popBackStack()
                navController.navigate(BottomNavItem.CheckList.screen_route + "?inputDemirFiyatList=" + "?inputCheckList=${Gson().toJson(checkList)}")
            }else{
                navController.popBackStack()
                navController.navigate(BottomNavItem.CheckList.fullRoute)
            }
        }
    }

    //Compose Components
    Column() {
        Surface(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.CenterStart) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Icon(
                        modifier = Modifier
                            .padding(12.dp, 0.dp, 0.dp, 0.dp)
                            .size(40.dp),
                        imageVector = Icons.Default.PriceCheck,
                        contentDescription = null)
                    CustomText(
                        modifier = Modifier.padding(2.dp,12.dp,0.dp,0.dp),
                        fontSize = 10.sp,
                        text = "SADECE FİYAT;",
                        fontWeight = FontWeight.ExtraBold)
                    CustomText(
                        modifier = Modifier.padding(2.dp,12.dp,0.dp,0.dp),
                        fontSize = 10.sp,
                        text = "inşaat demiri")
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (isRefreshing) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onBackground
                )
            } else {

                CustomHorizontalPager(mainViewModel)

            }
        }
    }
}