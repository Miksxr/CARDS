package com.example.cards.dependencies

import android.content.Context
import androidx.room.Room
import com.example.cards.database.CardDao
import com.example.cards.database.GameDatabase
import com.example.cards.dataclass.Card
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GameRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cardDao: CardDao
) {

    suspend fun loadCards(): List<Card> {
        return cardDao.getAllCards() // Загрузка всех карт из базы данных
    }
}

