package com.example.cards.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cards.dataclass.Card

@Dao
interface CardDao {
    //добавляет список карт в базу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cards: List<Card>)

    //извлекает все карты
    @Query("SELECT * FROM cards")
    suspend fun getAllCards(): List<Card>
}
