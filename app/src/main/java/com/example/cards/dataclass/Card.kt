package com.example.cards.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val attack: Int,
    var health: Int, // Изменяемое поле здоровья
    val moveSpeed: Int,
    val cost: Int,
    val imageResId: Int // Ресурс изображения карты
)

