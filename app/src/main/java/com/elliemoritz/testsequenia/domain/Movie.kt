package com.elliemoritz.testsequenia.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double?,
    val imageUrl: String?,
    val description: String?,
    val genres: Set<String>
) : Parcelable
