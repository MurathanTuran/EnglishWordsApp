package com.turanapps.englishwordsapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.turanapps.englishwordsapp.Errors.Error
import com.turanapps.englishwordsapp.Model.Word
import com.turanapps.englishwordsapp.RoomDB.EnglishWordsDatabase
import kotlinx.coroutines.*

class ListWordsViewModel(application: Application) : AndroidViewModel(application){

    private val appDatabase = EnglishWordsDatabase.getInstance(application.applicationContext)

    var isEmpty = MutableLiveData<Boolean>()

    private var readFromRoomDBErrorB = MutableLiveData<Boolean?>()
    private val readFromRoomDBError = "Error While Reading From Room Database"
    val readFromRoomDBErrorEntity = Error(readFromRoomDBErrorB, readFromRoomDBError)

    fun getDatasetFromRoomDB(forWhat: String): ArrayList<Word>{
        var itemList = ArrayList<Word>()

        runBlocking {
            try {
                if(forWhat.equals("learnedWordsList")){
                    itemList = getDatasetForLearnedWordsList()
                }
                else if(forWhat.equals("notLearnedWordsList")){
                    itemList = getDatasetForNotLearnedWordsList()
                }
            } catch (e: Exception){
                readFromRoomDBErrorB.value = true
            }
        }

        checkIsEmpty(itemList)

        return itemList
    }

    private suspend fun getDatasetForLearnedWordsList(): ArrayList<Word>{
        return withContext(Dispatchers.IO) {
            appDatabase.englishWordsDao().getAllLearnedWords() as ArrayList<Word>
        }
    }

    private suspend fun getDatasetForNotLearnedWordsList(): ArrayList<Word>{
        return withContext(Dispatchers.IO) {
            appDatabase.englishWordsDao().getAllNotLearnedWords() as ArrayList<Word>
        }
    }

    private fun checkIsEmpty(itemList: ArrayList<Word>){
        isEmpty.value = itemList.size == 0
    }

}