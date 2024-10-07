package com.example.cards.dataclass

data class Cell(
    val x: Int,
    val y: Int,
    var card: Card? = null, // Карта на клетке (если она есть)
    var ownerId: Int? = null
)
