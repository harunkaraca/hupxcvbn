package com.hk.hulkapps.di

import android.content.Context
import android.content.SharedPreferences
import com.hk.hulkapps.data.BaseRepository
import dagger.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppModuleBinds::class,ViewModelBuilderModule::class,SubcomponentsModule::class])
interface AppComponent {


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    val baseRepository: BaseRepository
    val context: Context
    val retrofit:Retrofit
    val okHttpClient: OkHttpClient
    val httpLoggingInterceptor: HttpLoggingInterceptor
    val sharedPreferences: SharedPreferences


}