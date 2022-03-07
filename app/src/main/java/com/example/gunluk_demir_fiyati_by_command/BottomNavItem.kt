package com.example.gunluk_demir_fiyati_by_command

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String, var arguments: String){

    object Main: BottomNavItem("Main",R.drawable.ic_launcher_foreground,"main", ""){
        val fullRoute = screen_route + arguments
    }
}
