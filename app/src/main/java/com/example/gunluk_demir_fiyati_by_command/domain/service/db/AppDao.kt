package com.example.gunluk_demir_fiyati_by_command.domain.service.db


import androidx.room.*
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(demirFiyat: DemirFiyat)

    @Query("SELECT * FROM dbtable")
    suspend fun getAll(): List<DemirFiyat>

    @Query("DELETE FROM dbtable")
    suspend fun deleteAll()

}