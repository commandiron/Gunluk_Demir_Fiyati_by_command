package com.example.gunluk_demir_fiyati_by_command.domain.use_case

data class UseCases (
    //Main Screen
    val getData: GetData,
    val insertCheckListToDb: InsertCheckListToDb,
    val getCheckListFromDb: GetCheckListFromDb
)