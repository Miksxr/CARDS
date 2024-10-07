package com.example.cards.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Card(
    val ownerId: Int? = null,
    val name: String,
    val attack: Int,
    var health: Int, // Изменяемое поле здоровья
    val moveSpeed: Int,
    val cost: Int,
    val imageResId: Int // Ресурс изображения карты
)

