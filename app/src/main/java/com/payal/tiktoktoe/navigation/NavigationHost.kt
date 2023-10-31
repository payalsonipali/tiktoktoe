package com.payal.tiktoktoe.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.payal.tiktoktoe.view.Board
import com.payal.tiktoktoe.view.SelectOptions
import com.payal.tiktoktoe.viewmodel.GameViewModel

@Composable
fun NavigationHost(navHostController: NavHostController){
    NavHost(
        navController = navHostController,
        startDestination = NavigationGraph.SelectOptions.route
    ) {
        composable(route = NavigationGraph.SelectOptions.route) {
            SelectOptions(navHostController)
        }

        composable(route = NavigationGraph.Game.route) {
            val viewModel: GameViewModel = hiltViewModel()
            val type =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<String>(
                    "type"
                )
            Board(viewModel = viewModel, type = type)
        }
    }
}