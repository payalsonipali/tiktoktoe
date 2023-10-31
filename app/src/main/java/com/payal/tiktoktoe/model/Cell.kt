package com.payal.tiktoktoe.model

data class Cell(var player: Player = Player.NONE)

enum class Player{
    NONE, X, O
}
