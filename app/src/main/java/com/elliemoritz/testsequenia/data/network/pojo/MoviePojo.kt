package com.elliemoritz.testsequenia.data.network.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviePojo(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("localized_name")
    @Expose
    val localizedName: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("year")
    @Expose
    val year: Int,

    @SerializedName("rating")
    @Expose
    val rating: Double?,

    @SerializedName("image_url")
    @Expose
    val imageUrl: String?,

    @SerializedName("description")
    @Expose
    val description: String?,

    @SerializedName("genres")
    @Expose
    val genres: Set<String>
)