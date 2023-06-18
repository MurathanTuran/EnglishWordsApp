package com.turanapps.englishwordsapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.turanapps.englishwordsapp.Errors.Error
import com.turanapps.englishwordsapp.Model.Word
import com.turanapps.englishwordsapp.RoomDB.EnglishWordsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

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
        var itemList = ArrayList<Word>()
        val deferredResult = viewModelScope.async(Dispatchers.IO) {
            itemList = appDatabase.englishWordsDao().getAllLearnedWords() as ArrayList<Word>
        }
        deferredResult.await()
        return itemList
    }

    private suspend fun getDatasetForNotLearnedWordsList(): ArrayList<Word>{
        var itemList = ArrayList<Word>()
        val deferredResult = viewModelScope.async(Dispatchers.IO) {
            itemList = appDatabase.englishWordsDao().getAllNotLearnedWords() as ArrayList<Word>
        }
        deferredResult.await()
        return itemList
    }

    private fun checkIsEmpty(itemList: ArrayList<Word>){
        isEmpty.value = itemList.size == 0
    }

}