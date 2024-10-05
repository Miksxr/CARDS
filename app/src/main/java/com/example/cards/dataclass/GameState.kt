package com.example.cards.dataclass

data class GameState(
    var currentPlayer: Player,
    var opponentPlayer: Player,
    val board: List<List<Cell>>, // Двумерный массив клеток
    var turnNumber: Int = 1 // Счётчик раундов
)
