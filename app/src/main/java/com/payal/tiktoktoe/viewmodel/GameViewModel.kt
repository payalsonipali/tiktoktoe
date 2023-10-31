package com.payal.tiktoktoe.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payal.tiktoktoe.model.Cell
import com.payal.tiktoktoe.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    var count = 0

    private val _board = MutableLiveData<List<Cell>>()
    val board: LiveData<List<Cell>> = _board

    private val _currentPlayer = MutableLiveData<Player>(Player.X)
    val currentPlayer = _currentPlayer

    var currentIndex = mutableStateOf<Int?>(null)
    var winner = mutableStateOf<String?>(null)

    init {
        _board.value = List(9) { Cell() }
        currentIndex = mutableStateOf(null)
    }

    fun onCellClick(index: Int): Pair<Int, Player> {
        Log.d("taggg", "indexxxxxx : $index")
        currentIndex.value = index
        var winner = Player.NONE
        val currentBoard = _board.value!!.toMutableList()
        if (currentBoard[index].player == Player.NONE) {
            count++
            currentBoard[index].player = _currentPlayer.value!!
            _board.value = currentBoard
            if (count > 4) {
                winner = checkWinner()
            }
            _currentPlayer.value = if (_currentPlayer.value == Player.X) Player.O else Player.X
        }
        return Pair(count, winner)
    }

    private fun checkWinner(): Player {
        val list = board.value!!
        for (condition in winningConditions) {
            if (condition.all { list[it] == list[condition[0]] }) {
                return list[condition[0]].player
            }
        }
        return Player.NONE
    }

    fun restart() {
        _board.value = List(9) { Cell() }
        count = 0
    }

    fun selectRandom(): Pair<Int, Player> {
        lateinit var result: Pair<Int, Player>
        val currentBoard =
            board.value?.withIndex()?.filter { it.value.player == Player.NONE }?.map { it.index }
                ?.random()
        currentIndex.value = currentBoard
        println("random : $currentBoard")
        if (currentBoard != null) {
            result = onCellClick(currentBoard)
        }
        return result
    }
}

val winningConditions = listOf(
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    listOf(0, 4, 8),
    listOf(2, 4, 6),
    listOf(0, 3, 6),
    listOf(1, 4, 7),
    listOf(2, 5, 8)
)