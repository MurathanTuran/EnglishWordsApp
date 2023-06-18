package com.turanapps.englishwordsapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @SerializedName("English") val english: String = "",

    @SerializedName("Turkish") val turkish: String = "",

    var learned:Boolean = false
)
