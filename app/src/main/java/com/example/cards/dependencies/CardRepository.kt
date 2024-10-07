package com.example.cards.dependencies

import com.example.cards.R
import com.example.cards.dataclass.Card

object CardRepository {

    private val cardList = listOf(
        Card(name = "Igor", attack = 8, health = 4, moveSpeed = 2, cost = 5, imageResId = R.drawable.card_igor),
        Card(name = "Artem", attack = 8, health = 8, moveSpeed = 2, cost = 4, imageResId = R.drawable.card_artem),
        Card(name = "Kirill", attack = 3, health = 12, moveSpeed = 1, cost = 3, imageResId = R.drawable.card_kirill),
        Card(name = "Max", attack = 4, health = 6, moveSpeed = 3, cost = 2, imageResId = R.drawable.card_max),
        Card(name = "Alex", attack = 4, health = 8, moveSpeed = 1, cost = 1, imageResId = R.drawable.card_alex),
        Card(name = "Sergo", attack = 10, health = 10, moveSpeed = 3, cost = 8, imageResId = R.drawable.card_sergo)
    )

    fun getAllCards(): List<Card> {
        return cardList
    }
}