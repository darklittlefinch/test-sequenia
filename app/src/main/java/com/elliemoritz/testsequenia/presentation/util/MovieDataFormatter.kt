package com.elliemoritz.testsequenia.presentation.util

import com.elliemoritz.testsequenia.domain.Genre
import com.elliemoritz.testsequenia.domain.Movie
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

fun getGenresFromMoviesList(
    moviesList: List<Movie>
): List<Genre> = moviesList
    .flatMap { it.genres }
    .toSortedSet()
    .map { Genre(getCapitalizedString(it), false) }

fun getCapitalizedString(string: String): String {
    val firstLetter = string.substring(0, 1).uppercase()
    val restOfString = string.substring(1).lowercase()
    return firstLetter + restOfString
}