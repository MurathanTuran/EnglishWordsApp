package com.turanapps.englishwordsapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.turanapps.englishwordsapp.Errors.Error
import com.turanapps.englishwordsapp.Model.Word
import com.turanapps.englishwordsapp.RoomDB.EnglishWordsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class WordViewModel(application: Application) : AndroidViewModel(application){

    private val appDatabase = EnglishWordsDatabase.getInstance(application.applicationContext)

    var word = MutableLiveData<Word?>()

    private var readFromRoomDBErrorB = MutableLiveData<Boolean?>()
    private val readFromRoomDBError = "Error While Reading From Room Database"
    val readFromRoomDBErrorEntity = Error(readFromRoomDBErrorB, readFromRoomDBError)


    private var updateWordErrorB = MutableLiveData<Boolean?>()
    private val updateWordError = "Error While Updating to Room Database"
    val updateWordErrorEntity = Error(updateWordErrorB, updateWordError)


    fun getDataFromRoomDB(forWhat: String){
        runBlocking {
            try {
                if(forWhat.equals("learnedWord")){
                    word.value = appDatabase.englishWordsDao().getRandomLearnedWord()
                }
                else if(forWhat.equals("notLearnedWord")){
                    word.value = appDatabase.englishWordsDao().getRandomNotLearnedWord()
                }
            } catch (e: Exception){
                readFromRoomDBErrorB.value = true
            }
        }

    }

    fun updateWord(word: Word, forWhat: String){
        try {
            runBlocking {
                val deferredResult = async(Dispatchers.IO) {
                    appDatabase.englishWordsDao().updateWord(word)
                }
                deferredResult.await()
            }
            getDataFromRoomDB(forWhat)
        }catch (e: Exception){
            updateWordErrorB.value = true
        }
    }


}