package com.example.gunluk_demir_fiyati_by_command.domain.service.db


import androidx.room.*
import com.example.gunluk_demir_fiyati_by_command.domain.model.CityCheck


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cityCheckList: List<CityCheck>)

    @Query("SELECT * FROM dbtable")
    suspend fun getAll(): List<CityCheck>

    @Query("DELETE FROM dbtable")
    suspend fun deleteAll()

}