package com.turanapps.englishwordsapp.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.turanapps.englishwordsapp.Model.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class EnglishWordsDatabase : RoomDatabase() {
    abstract fun englishWordsDao(): EnglishWordsDao

    companion object {
        @Volatile
        private var INSTANCE: EnglishWordsDatabase? = null

        fun getInstance(context: Context): EnglishWordsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EnglishWordsDatabase::class.java,
                    "english_words"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}