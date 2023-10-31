package com.payal.tiktoktoe.view

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.payal.tiktoktoe.model.Player
import com.payal.tiktoktoe.viewmodel.GameViewModel

@Composable
fun Board(viewModel: GameViewModel, type: String?) {
    val cells by viewModel.board.observeAsState()
    val lazyListState = rememberLazyGridState()

    var clickedCellIndex by viewModel.currentIndex

    LaunchedEffect(clickedCellIndex) {
        clickedCellIndex?.let {
            lazyListState.scrollToItem(0)
        }
    }

    LazyVerticalGrid(
        state = lazyListState,
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        cells?.let {
            items(it.size) { index ->
                val cell = cells?.get(index)

                Button(
                    shape = RectangleShape,
                    onClick = {
                        if(type == "two player"){playGame(viewModel, index)}
                        else {playWithRobot(viewModel, index)}
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .height(80.dp)
                        .width(80.dp)
                ) {
                    val buttonText = when (cell?.player) {
                        Player.X -> "X"
                        Player.O -> "O"
                        else -> ""
                    }
                    Text(text = buttonText, fontSize = 20.sp, color = Color.White)
                }
            }
        }
    }

    if (viewModel.winner.value != null) {
        AlertDialog(
            onDismissRequest = {  },
            title = { if(viewModel.winner.value == "TIE")Text(text = "Match TIE") else Text(text = "Player ${viewModel.winner.value} Winner")},
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.winner.value = null
                        viewModel.restart()
                    }) {
                    Text(text = "New Match")
                }
            }
        )
    }
}

fun playWithRobot(viewModel: GameViewModel, index: Int){
    val winnerPlayer = viewModel.onCellClick(index)
    Log.d("taggg","winnerPlayer : $winnerPlayer")
    if (winnerPlayer.second != Player.NONE) {
        viewModel.winner.value = winnerPlayer.second.name
    }
    if(winnerPlayer.first >= 9 && winnerPlayer.second == Player.NONE){
        viewModel.winner.value = "TIE"
    }
    if(winnerPlayer.first < 9 && winnerPlayer.second == Player.NONE) {
        Handler(Looper.getMainLooper()).postDelayed({
            //Do something after 100ms
            val winnerPlayer = viewModel.selectRandom()
            Log.d("taggg", "winnerPlayer : $winnerPlayer")
            if (winnerPlayer.second != Player.NONE) {
                viewModel.winner.value = winnerPlayer.second.name
            }
            if (winnerPlayer.first >= 9 && winnerPlayer.second == Player.NONE) {
                viewModel.winner.value = "TIE"
            }
        }, 300)
    }
}

fun playGame(viewModel: GameViewModel, index: Int){
    val winnerPlayer = viewModel.onCellClick(index)
    Log.d("taggg","winnerPlayer : $winnerPlayer")
    if (winnerPlayer.second != Player.NONE) {
        viewModel.winner.value = winnerPlayer.second.name
    }
    if(winnerPlayer.first >= 9 && winnerPlayer.second == Player.NONE){
        viewModel.winner.value = "TIE"
    }
}