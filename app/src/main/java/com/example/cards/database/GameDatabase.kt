package com.example.cards.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cards.R
import com.example.cards.dataclass.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

//@Database(entities = [Card::class], version = 1)
//abstract class GameDatabase : RoomDatabase() {
//    abstract fun cardDao(): CardDao
//
//    companion object {
//        private var INSTANCE: GameDatabase? = null
//
//        fun getDatabase(context: Context): GameDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    GameDatabase::class.java,
//                    "game_database"
//                )
//                    .fallbackToDestructiveMigration() // Только разрушаем миграцию
//                    .addCallback(object : RoomDatabase.Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            CoroutineScope(Dispatchers.IO).launch {
//                                getDatabase(context).cardDao().insertAll(
//                                    listOf(
//                                        Card(name = "Igor", attack = 8, health = 4, moveSpeed = 2, cost = 5, imageResId = R.drawable.card_igor),
//                                        Card(name = "Artem", attack = 8, health = 8, moveSpeed = 2, cost = 4, imageResId = R.drawable.card_artem),
//                                        Card(name = "Kirill", attack = 3, health = 12, moveSpeed = 1, cost = 3, imageResId = R.drawable.card_kirill),
//                                        Card(name = "Max", attack = 4, health = 6, moveSpeed = 3, cost = 2, imageResId = R.drawable.card_max),
//                                        Card(name = "Alex", attack = 4, health = 8, moveSpeed = 1, cost = 1, imageResId = R.drawable.card_alex)
//                                    )
//                                )
//                            }
//                        }
//                    })
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}

