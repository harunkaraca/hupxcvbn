package com.hk.hulkapps.data.service

import com.hk.hulkapps.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface Api {

    @GET("/movieslist")
    suspend fun getMovies(): Response<Movie>
}