package com.payal.tiktoktoe.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectOptions(navHostController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(25.dp)),
            onClick = {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "type",
                    "two player"
                )
                navHostController.navigate("game")
            }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Two Players",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                )
            }

        }
        Card(
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(25.dp)),
            onClick = {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "type",
                    "robot"
                )
                navHostController.navigate("game")
            }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "With Robot",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                )
            }

        }
    }
}