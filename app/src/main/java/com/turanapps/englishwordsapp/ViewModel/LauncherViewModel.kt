package com.turanapps.englishwordsapp.ViewModel

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.turanapps.englishwordsapp.Errors.Error
import com.turanapps.englishwordsapp.Model.Word
import com.turanapps.englishwordsapp.RoomDB.EnglishWordsDatabase
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class LauncherViewModel(application: Application): AndroidViewModel(application) {

    private val appDatabase = EnglishWordsDatabase.getInstance(application.applicationContext)
    private val sharedPref = application.getSharedPreferences("com.turanapps.englishwordsapp", AppCompatActivity.MODE_PRIVATE)
    private val inputStream = application.applicationContext.assets.open("dictionary.json")

    var toMainFragmentB = MutableLiveData<Boolean?>()

    private var readJsonErrorB = MutableLiveData<Boolean?>()
    private val readJsonError = "Error While Reading Json"
    val readJsonErrorEntity = Error(readJsonErrorB, readJsonError)

    private var writeToRoomDBErrorB = MutableLiveData<Boolean?>()
    private val writeToRoomDBError = "Error While Writing to Room Database"
    val writeToRoomDBErrorEntity = Error(writeToRoomDBErrorB, writeToRoomDBError)

    fun whatToDo(){
        if(isFirstTimeLaunch()){
            val words = readJsonFromAssets(inputStream)
            viewModelScope.launch {
                val deferredResult = async(Dispatchers.IO) {
                    try {
                        appDatabase.englishWordsDao().insertAllWords(words)
                    }catch (e: Exception){
                        writeToRoomDBErrorB.value = true
                    }
                }

                val startTime = System.currentTimeMillis()
                deferredResult.await()
                val endTime = System.currentTimeMillis()

                val elapsedTime = endTime - startTime
                val remainingTime = 2000 - elapsedTime

                if (remainingTime > 0) {
                    delay(remainingTime)
                    toMainFragmentB.value = true
                }
                else{
                    toMainFragmentB.value = true
                }
            }
        }
        else{
            viewModelScope.launch {
                delay(2000)
                toMainFragmentB.value = true
            }
        }

    }

    private fun isFirstTimeLaunch(): Boolean {
        val isFirstTime = sharedPref.getBoolean("isFirstTimeLaunch", true)

        if (isFirstTime) {
            val editor = sharedPref.edit()
            editor.putBoolean("isFirstTimeLaunch", false)
            editor.apply()
        }
        return isFirstTime
    }

    private fun readJsonFromAssets(inputStream: InputStream): List<Word> {
        //val jsonContent = StringBuilder()
        var jsonContent:String = String()
        try {
            //val inputStream = applicationContext.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            //var line: String?


            /*
            while (bufferedReader.readLine().also { line = it } != null) {
                jsonContent.append(line)
            }
             */


            jsonContent = bufferedReader.readText()
            bufferedReader.close()
        } catch (e: Exception) {
            readJsonErrorB.value = true
        }

        val gson = Gson()
        //val words: List<Word> = gson.fromJson(jsonContent.toString(), Array<Word>::class.java).toList()
        val words: List<Word> = gson.fromJson(jsonContent, Array<Word>::class.java).toList()

        return words
    }

}