package com.example.gunluk_demir_fiyati_by_command.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.gunluk_demir_fiyati_by_command.R
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.android.material.animation.AnimationUtils.lerp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun CustomHorizontalPager(mainViewModel: MainViewModel) {

    val maskedDemirFiyatList = mainViewModel.maskedDemirFiyatList.value

    HorizontalPager(

        count = maskedDemirFiyatList.size,
        // Add 32.dp horizontal padding to 'center' the pages
        contentPadding = PaddingValues(horizontal = 32.dp),
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Card(
            Modifier
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        0.85f,
                        1f,
                        1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        0.5f,
                        1f,
                        1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Box {
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
                                data = maskedDemirFiyatList[page].fotoUrl,
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
                            if(maskedDemirFiyatList.isNotEmpty()){
                                CustomText(
                                    fontSize = 30.sp,
                                    text = maskedDemirFiyatList[page].bolge)
                                Spacer(modifier = Modifier.width(5.dp))
                                val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(
                                    Calendar.getInstance().time)
                                CustomText(
                                    fontSize = 10.sp,
                                    text = "$currentDate tarihli demir fiyatları (KDV DAHİL, NAKLİYE HARİÇ):")
                                Spacer(modifier = Modifier.width(5.dp))
                                CustomText(
                                    fontSize = 22.sp,
                                    text = "Φ8: ${maskedDemirFiyatList[page].fi8Fiyat}")
                                Spacer(modifier = Modifier.width(5.dp))
                                CustomText(
                                    fontSize = 22.sp,
                                    text = "Φ10: ${maskedDemirFiyatList[page].fi10Fiyat}")
                                Spacer(modifier = Modifier.width(5.dp))
                                CustomText(
                                    fontSize = 22.sp,
                                    text = "Φ12-32: ${maskedDemirFiyatList[page].fi1232Fiyat}")
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
                                mainViewModel.getDataFromJsoup()
                            })
                    }
                }
            }
            }
        }
    }
