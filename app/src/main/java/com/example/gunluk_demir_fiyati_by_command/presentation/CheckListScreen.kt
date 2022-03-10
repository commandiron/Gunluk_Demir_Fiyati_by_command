package com.example.gunluk_demir_fiyati_by_command.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.gunluk_demir_fiyati_by_command.domain.model.CityCheck
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.example.gunluk_demir_fiyati_by_command.presentation.navigation.BottomNavItem
import com.example.gunluk_demir_fiyati_by_command.ui.theme.background
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun CheckListScreen(
    inputDemirFiyatList: String, //List<DemirFiyat>,
    inputCheckList: String, //List<CityCheck>,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController){

    val demirFiyatList : List<DemirFiyat> by remember {mutableStateOf(Gson().fromJson(inputDemirFiyatList, object : TypeToken<List<DemirFiyat>>() {}.type))}

    var checkList : List<CityCheck> by remember {mutableStateOf(listOf())}

    LaunchedEffect(key1 = inputCheckList){
        println(inputCheckList)
        if(inputCheckList.isNotEmpty()){
            if(inputCheckList != null){
                checkList = Gson().fromJson(inputCheckList, object : TypeToken<List<CityCheck>>() {}.type)
            }
        }
    }

    Column(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize()) {
        Surface(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(12.dp, 0.dp, 0.dp, 0.dp)
                            .size(30.dp),
                        imageVector = Icons.Default.Settings,
                        contentDescription = null
                    )
                    CustomText(
                        modifier = Modifier.padding(2.dp, 12.dp, 0.dp, 0.dp),
                        fontSize = 10.sp,
                        text = "AYARLAR",
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        val scrollState = rememberLazyListState()

        Box(modifier = Modifier
            .weight(1f)
            .padding(8.dp, 2.dp), contentAlignment = Alignment.Center) {
            Text(
                text = "Fiyat takibi için ana ekranda görmek istediğiniz şehirleri seçin;",
                textAlign = TextAlign.Center)
        }

        LazyColumn(
            modifier = Modifier
                .padding(8.dp, 2.dp)
                .fillMaxWidth()
                .weight(4f),
            state = scrollState){

            this.items(demirFiyatList) { demirFiyat: DemirFiyat ->
                var check by remember { mutableStateOf( false) }
                if(checkList.isNotEmpty()){
                    for(i in checkList){
                        if(i.id == demirFiyat.id){
                            check = i.isChecked
                        }
                    }
                }

                Card(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    elevation = 5.dp,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = MaterialTheme.colors.onBackground),
                            checked = check,
                            onCheckedChange = {
                            check = !check

                            if(checkList.contains(CityCheck(demirFiyat.id, demirFiyat.bolge, check)) || checkList.contains(
                                    CityCheck(demirFiyat.id, demirFiyat.bolge, !check)
                                )){
                                checkList.find { it.id == demirFiyat.id }?.let {
                                    it.isChecked = check
                                }
                            }else{
                                checkList = checkList + CityCheck(demirFiyat.id, demirFiyat.bolge, check)
                            }
                        })
                        Text(text = demirFiyat.bolge)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(4f))
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Surface(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)) {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "KAYDET VE GERİ GİT",
                    color = Color.Red,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(
                                BottomNavItem.Main.screen_route + "/${Gson().toJson(checkList)}"
                            )
                        })
            }
        }
    }
}