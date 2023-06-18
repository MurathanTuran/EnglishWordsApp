package com.turanapps.englishwordsapp.Errors

import androidx.lifecycle.MutableLiveData

data class Error(
    val errorB: MutableLiveData<Boolean?>,
    var error: String
)
