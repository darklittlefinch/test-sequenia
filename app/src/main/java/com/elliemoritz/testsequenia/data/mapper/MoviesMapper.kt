package com.elliemoritz.testsequenia.data.mapper

import com.elliemoritz.testsequenia.data.network.pojo.MoviePojo
import com.elliemoritz.testsequenia.domain.Movie

class MoviesMapper {

    private fun mapPojoToEntity(pojo: MoviePojo) = Movie(
        id = pojo.id,
        localizedName = pojo.localizedName,
        name = pojo.name,
        year = pojo.year,
        rating = pojo.rating,
        imageUrl = pojo.imageUrl,
        description = pojo.description,
        genres = pojo.genres
    )

    fun mapListPojoToListEntity(list: List<MoviePojo>) = list.map {
        mapPojoToEntity(it)
    }.sortedBy { it.localizedName }
}