package com.elliemoritz.testsequenia.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val name: String,
    val selected: Boolean
) : Parcelable