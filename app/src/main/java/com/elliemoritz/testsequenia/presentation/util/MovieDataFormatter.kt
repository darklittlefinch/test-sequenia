package com.elliemoritz.testsequenia.presentation.util

import kotlin.math.round

private const val EMPTY_STRING = ""
private const val GENRES_DELIMITER = ", "
private const val NO_RATING = "-"

fun formatGenres(genres: Set<String>?): String {

    if (genres.isNullOrEmpty()) {
        return EMPTY_STRING
    }

    val stringBuilder = StringBuilder()

    for (genre in genres) {
        stringBuilder.append(genre)
        stringBuilder.append(GENRES_DELIMITER)
    }

    return stringBuilder.toString()
}

fun formatRating(rating: Double?): String {
    if (rating == null) {
        return NO_RATING
    }
    val roundedRating = round(rating * 10) / 10
    return roundedRating.toString()
}