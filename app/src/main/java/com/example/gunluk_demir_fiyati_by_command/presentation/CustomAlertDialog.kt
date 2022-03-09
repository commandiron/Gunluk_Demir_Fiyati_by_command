package com.example.gunluk_demir_fiyati_by_command.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat


@Composable
fun CustomAlertDialog(
    demirFiyatListFromJsoup: MutableList<DemirFiyat>,
    onDismiss: () -> Unit,
    onConfirm: (List<DemirFiyat>) -> Unit = {}
) {

    val dialogText = "Add user via email".trimIndent()

    val demirFiyatList by remember {mutableStateOf(demirFiyatListFromJsoup)}

    var demirFiyatCheckList by remember {mutableStateOf<MutableList<DemirFiyat>>(mutableListOf())}

    LaunchedEffect(key1 = Unit){
        demirFiyatCheckList.addAll(demirFiyatList)
    }

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
                    onConfirm(demirFiyatCheckList)
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
                Text(text = dialogText)
                demirFiyatList.forEach { demirFiyat ->
                    var check by remember { mutableStateOf(demirFiyat.userCheck ?: false) }
                    Row() {
                        Checkbox(checked = check, onCheckedChange = {
                            check = !check
                            println(demirFiyat.id.toString() + it)
                            demirFiyatCheckList.removeAt(demirFiyat.id!! - 1)
                            demirFiyatCheckList.add(DemirFiyat(demirFiyat.id,demirFiyat.bolge,demirFiyat.fi8Fiyat,demirFiyat.fi10Fiyat,demirFiyat.fi1232Fiyat,check))
                            demirFiyatCheckList = demirFiyatCheckList.sortedBy{it.id}.toMutableList()
                        })
                        Text(text = demirFiyat.bolge)
                    }
                }
            }
        }
    )
}

