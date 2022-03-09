package com.example.gunluk_demir_fiyati_by_command.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dbtable")
data class DemirFiyat(
    @PrimaryKey
    val id: Int? = null,
    var bolge: String,
    var fi8Fiyat: String,
    var fi10Fiyat: String,
    var fi1232Fiyat: String,
    var userCheck: Boolean?
) {
}