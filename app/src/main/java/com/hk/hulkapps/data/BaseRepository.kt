package com.hk.hulkapps.data

import com.hk.hulkapps.data.model.Movie

interface BaseRepository {
    suspend fun getMovies(): com.hk.hulkapps.data.source.Result<Movie>
}