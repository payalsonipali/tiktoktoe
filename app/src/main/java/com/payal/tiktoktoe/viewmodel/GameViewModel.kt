package com.payal.tiktoktoe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payal.tiktoktoe.model.Cell
import com.payal.tiktoktoe.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    var count = 0

    private val _board = MutableLiveData<List<Cell>>()
    val board: LiveData<List<Cell>> = _board

    private val _currentPlayer = MutableLiveData<Player>(Player.X)
    val currentPlayer = _currentPlayer

    init {
        _board.value = List(9) { Cell() }
    }

    fun onCellClick(index: Int):Pair<Int, Player> {
        Log.d("taggg", "indexxxxxx : $index")
        var winner = Player.NONE
        val currentBoard = _board.value!!.toMutableList()
        if(currentBoard[index].player == Player.NONE) {
            count++
            currentBoard[index].player = _currentPlayer.value!!
            _board.value = currentBoard
            if(count > 4){
                winner = checkWinner()
            }
            _currentPlayer.value = if (_currentPlayer.value == Player.X) Player.O else Player.X
            Log.d("taggg", "current  : ${currentPlayer.value}  :::  board : ${board.value}")
        }
        return Pair(count,winner)
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

    fun restart(){
        _board.value = List(9) { Cell() }
        count = 0
    }

    private fun selectRandom():Int{
        var random = 0
        val currentBoard = _board.value!!.toMutableList()
        while(currentBoard[random].player != Player.NONE){
            random = Random.nextInt(0, 9)
        }
        println("random : $random")
        return random
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