package com.hk.hulkapps.ui.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.hulkapps.data.BaseRepository
import com.hk.hulkapps.data.model.Category
import com.hk.hulkapps.data.model.Movie
import com.hk.hulkapps.data.source.Result
import com.hk.hulkapps.data.source.Result.Success
import com.hk.hulkapps.data.source.Result.Error
import com.hk.hulkapps.data.source.Result.Loading
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel  @Inject constructor(private val baseRepository: BaseRepository, private val sharedPreferences: SharedPreferences):ViewModel() {

    private val _items = MutableLiveData<List<Category>>().apply { value = emptyList() }
    val items: LiveData<List<Category>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError = _isDataLoadingError

   init {
   }
    fun refresh(){
        loadMovies()
    }
    fun loadMovies(){
        Log.i("MainViewModel","run MainViewModel")
        viewModelScope.launch {
            val result=baseRepository.getMovies()
            if(result is Success){
                _isDataLoadingError.value = false
                _items.value = result.data.categories
            }else{
                _isDataLoadingError.value = false
                _items.value = emptyList()
            }
            _dataLoading.value = false
        }
    }
}