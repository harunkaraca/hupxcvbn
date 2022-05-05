package com.hk.hulkapps.data

import androidx.lifecycle.LiveData
import com.hk.hulkapps.data.model.Movie
import com.hk.hulkapps.data.source.BaseDataSource
import com.hk.hulkapps.di.AppModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import timber.log.Timber
import java.lang.IllegalStateException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultRepository @Inject constructor(
    @AppModule.RemoteDataSource private val remoteDataSource: BaseDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): BaseRepository {


    override suspend fun getMovies(): com.hk.hulkapps.data.source.Result<Movie> {
        val remoteMovies = remoteDataSource.getMovies()
        when (remoteMovies) {
            is Error -> Timber.w("Remote data source fetch failed")
            is com.hk.hulkapps.data.source.Result.Success -> {
                return remoteMovies
            }
            else -> throw IllegalStateException()
        }
        return com.hk.hulkapps.data.source.Result.Error(Exception("Error fetching from remote"))
    }


}