package com.payal.tiktoktoe.navigation

sealed class NavigationGraph(val route:String){
    object SelectOptions : NavigationGraph(route = "options")
    object Game : NavigationGraph(route = "game")
}