package com.example.cards.dependencies

import android.content.Context
import androidx.room.Room
import com.example.cards.dataclass.Card
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GameRepository {

    fun loadCards(): List<Card> {
        return CardRepository.getAllCards() // Загрузка всех карт из статического списка
    }

}
