package com.turanapps.englishwordsapp.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.turanapps.englishwordsapp.Model.Word

@Dao
interface EnglishWordsDao {

    @Insert
    suspend fun insertAllWords(words: List<Word>)

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * From words WHERE learned like true")
    suspend fun getAllLearnedWords(): List<Word>

    @Query("SELECT * From words WHERE learned like false")
    suspend fun getAllNotLearnedWords(): List<Word>

    @Query("SELECT * From words WHERE learned like true ORDER BY RANDOM()")
    suspend fun getAllLearnedWordsRandomly(): List<Word>

    @Query("SELECT * From words WHERE learned like false ORDER BY RANDOM()")
    suspend fun getAllNotLearnedWordsRandomly(): List<Word>

    @Query("SELECT * FROM words WHERE learned = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomLearnedWord(): Word?

    @Query("SELECT * FROM words WHERE learned = 0 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomNotLearnedWord(): Word?

    @Query("SELECT COUNT(*) FROM words")
    suspend fun getEntityCount(): Int

    @Query("SELECT COUNT(*) FROM words WHERE learned like true")
    suspend fun getLearnedWordsCount(): Int

    @Query("SELECT COUNT(*) FROM words WHERE learned like true")
    suspend fun getNotLearnedWordsCount(): Int

    @Query("SELECT * FROM words WHERE id = :id")
    suspend fun getWordById(id: Int): Word?

    @Update
    suspend fun updateWord(word: Word)

}