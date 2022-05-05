package com.hk.hulkapps.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("categories" ) var categories : ArrayList<Category> = arrayListOf()
)

data class Category (
    @SerializedName("name"   ) var name   : String?           = null,
    @SerializedName("videos" ) var videos : ArrayList<Video> = arrayListOf()
)

data class Video (
    @SerializedName("description" ) var description : String?           = null,
    @SerializedName("sources"     ) var sources     : ArrayList<String> = arrayListOf(),
    @SerializedName("subtitle"    ) var subtitle    : String?           = null,
    @SerializedName("thumb"       ) var thumb       : String?           = null,
    @SerializedName("title"       ) var title       : String?           = null
)
