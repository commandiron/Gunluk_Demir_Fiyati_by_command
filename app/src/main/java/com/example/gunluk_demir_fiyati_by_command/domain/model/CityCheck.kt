package com.example.gunluk_demir_fiyati_by_command.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dbtable")
data class CityCheck(
    @PrimaryKey
    val id: Int? = null,
    var bolge: String,
    var isChecked: Boolean
) {
}