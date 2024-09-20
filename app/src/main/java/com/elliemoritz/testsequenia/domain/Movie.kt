package com.elliemoritz.testsequenia.domain

data class Movie(
    val id: Int,
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double,
    val imageUrl: String,
    val description: String,
    val genres: Set<String>
)
