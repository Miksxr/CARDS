package com.example.cards.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Player(
    val id: Int,
    val name: String,
    var health: Int = 20,
    var mana: Int = 10, // Изначальные монеты
    val hand: MutableList<Card> = mutableListOf(), // Карты на руке
    val deck: MutableList<Card> = mutableListOf() // Колода карт
)
