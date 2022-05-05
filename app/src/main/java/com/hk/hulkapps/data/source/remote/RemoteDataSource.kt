package com.hk.hulkapps.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hk.hulkapps.data.model.Movie
import com.hk.hulkapps.data.service.Api
import com.hk.hulkapps.data.source.BaseDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.hk.hulkapps.data.source.Result.Success
import com.hk.hulkapps.data.source.Result.Error
class RemoteDataSource internal constructor(private val api: Api, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    BaseDataSource {

    override suspend fun getMovies(): com.hk.hulkapps.data.source.Result<Movie> {
        try {
            api.getMovies().let {response->
                if(response.isSuccessful){
                    return Success(response.body()!!)
                }else return Error(Exception("Error occured"))
            }
        } catch (cause: Exception) {
            return Error(cause)
        }
    }


}
