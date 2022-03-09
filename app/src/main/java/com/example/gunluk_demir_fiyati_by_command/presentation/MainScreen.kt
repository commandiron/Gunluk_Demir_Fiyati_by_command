package com.example.gunluk_demir_fiyati_by_command.ui.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.gunluk_demir_fiyati_by_command.MainScreenView
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.example.gunluk_demir_fiyati_by_command.presentation.MainViewModel
import com.example.gunluk_demir_fiyati_by_command.R
import com.example.gunluk_demir_fiyati_by_command.presentation.CustomAlertDialog
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
    isFabClicked: Boolean) {

    val demirFiyatListFromDb = mainViewModel.demirFiyatListFromDb.value
    val demirFiyatListFromJsoup = mainViewModel.demirFiyatListFromJsoup.value
    val isRefreshing by remember { mainViewModel.isRefreshing }

    //Alert Dialog For CheckList
    var showAlertDialog by remember { mutableStateOf(false) }
    if (showAlertDialog) {
        CustomAlertDialog(
            demirFiyatListFromJsoup = demirFiyatListFromJsoup, //İlk geldiğinde db'den çekmiyor.///////////////////////////////////////////
            onDismiss = {showAlertDialog = !showAlertDialog},
            onConfirm = {
                showAlertDialog = !showAlertDialog
                mainViewModel.insertDataWithChackList(it)
            })
    }

    var flag by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = isFabClicked){
        if(flag){
            flag = false
        }else{
            showAlertDialog = !showAlertDialog
        }
    }
    
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
                Card(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .width(320.dp)
                        .height(320.dp),
                    elevation = 20.dp,
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            painter = rememberImagePainter(
                                data = "https://upload.wikimedia.org/wikipedia/commons/5/53/Bosphorus_Bridge_%28235499411%29.jpeg",
                                builder = {
                                    crossfade(true)
                                    placeholder(drawableResId = R.drawable.ic_launcher_foreground)
                                }
                            ),
                            contentDescription = null
                        )

                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if(demirFiyatListFromDb.isNotEmpty()){
                                CustomText(
                                    fontSize = 30.sp,
                                    text = demirFiyatListFromDb[0].bolge)
                                Spacer(modifier = Modifier.width(5.dp))
                                val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
                                CustomText(
                                    fontSize = 10.sp,
                                    text = "$currentDate tarihli demir fiyatları (KDV DAHİL, NAKLİYE HARİÇ):")
                                Spacer(modifier = Modifier.width(5.dp))
                                CustomText(
                                    fontSize = 22.sp,
                                    text = "Φ8: ${demirFiyatListFromDb[0].fi8Fiyat}")
                                Spacer(modifier = Modifier.width(5.dp))
                                CustomText(
                                    fontSize = 22.sp,
                                    text = "Φ10: ${demirFiyatListFromDb[0].fi10Fiyat}")
                                Spacer(modifier = Modifier.width(5.dp))
                                CustomText(
                                    fontSize = 22.sp,
                                    text = "Φ12-32: ${demirFiyatListFromDb[0].fi1232Fiyat}")
                            }
                        }

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {

                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                mainViewModel.getAllDataFromDb()
                        })
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.onPrimary,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current
) {

    androidx.compose.material.Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = style
    )
}