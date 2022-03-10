package com.example.gunluk_demir_fiyati_by_command.presentation.navigation

import com.example.gunluk_demir_fiyati_by_command.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String, var arguments: String){

    object Main: BottomNavItem("Main", R.drawable.ic_launcher_foreground,"main", "/{inputCheckList}"){
        val fullRoute = screen_route + arguments
    }

    object CheckList: BottomNavItem("CheckList", R.drawable.ic_launcher_foreground,"checklist", "?inputDemirFiyatList={inputDemirFiyatList}" + "?inputCheckList={inputCheckList}"){
        val fullRoute = screen_route + arguments
    }

    //"/{inputDemirFiyatList}" + "/{inputCheckList}"
}
