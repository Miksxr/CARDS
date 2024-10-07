package com.example.cards.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cards.dependencies.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class) // Глобальный скоуп для приложения
//object DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): GameDatabase {
//        return GameDatabase.getDatabase(context)
//    }
//
//    @Provides
//    fun provideCardDao(database: GameDatabase): CardDao {
//        return database.cardDao()
//    }
//
//    @Provides
//    fun provideRepository(@ApplicationContext context: Context,
//                          dao: CardDao) : GameRepository {
//        return GameRepository(context, dao)
//    }
//}


