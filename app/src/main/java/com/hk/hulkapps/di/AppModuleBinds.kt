package com.hk.hulkapps.di

import com.hk.hulkapps.data.BaseRepository
import com.hk.hulkapps.data.DefaultRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {
    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultRepository): BaseRepository
}
