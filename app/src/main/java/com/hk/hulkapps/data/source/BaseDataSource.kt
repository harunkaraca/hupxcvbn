package com.hk.hulkapps.data.source

import androidx.lifecycle.LiveData
import com.hk.hulkapps.data.model.Movie

interface BaseDataSource {
    suspend fun getMovies(): Result<Movie>
}