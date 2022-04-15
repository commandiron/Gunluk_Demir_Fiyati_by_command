package com.example.gunluk_demir_fiyati_by_command.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gunluk_demir_fiyati_by_command.domain.model.CityCheck
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat


@Composable
fun CustomAlertDialog(
    inputDemirFiyatList: List<DemirFiyat>,
    inputCheckList: List<CityCheck>,
    onDismiss: () -> Unit,
    onConfirm: (List<CityCheck>) -> Unit = {}
) {

    val demirFiyatList by remember {mutableStateOf(inputDemirFiyatList)}
    var checkList by remember {mutableStateOf(inputCheckList)}

    androidx.compose.material.AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(checkList)
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "OK")
            }
        },
        title = {
            Text(text = "Fiyatı'nı görmek istediğini şehirleri seçiniz;", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        },
        text = {
            Column() {
                Text(text = "dialogText")

                demirFiyatList.forEach { demirFiyat ->
                    var check by remember { mutableStateOf( false) }
                    if(checkList.isNotEmpty()){
                        for(i in checkList){
                            if(i.id == demirFiyat.id){
                                check = i.isChecked
                            }
                        }
                    }
                    Row() {
                        Checkbox(checked = check, onCheckedChange = {
                            check = !check

                            if(checkList.contains(CityCheck(demirFiyat.id, demirFiyat.bolge, check)) || checkList.contains(CityCheck(demirFiyat.id, demirFiyat.bolge, !check))){
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
    )
}

