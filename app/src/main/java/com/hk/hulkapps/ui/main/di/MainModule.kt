package com.hk.hulkapps.ui.main.di

import androidx.lifecycle.ViewModel
import com.hk.hulkapps.data.BaseRepository
import com.hk.hulkapps.data.service.Api
import com.hk.hulkapps.di.ViewModelKey
import com.hk.hulkapps.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewmodel: MainViewModel): ViewModel

}

