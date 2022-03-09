package com.example.gunluk_demir_fiyati_by_command.domain.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat

@Database(entities = arrayOf(DemirFiyat::class), version = 9)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}