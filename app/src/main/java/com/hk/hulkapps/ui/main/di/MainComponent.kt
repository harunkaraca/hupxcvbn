package com.hk.hulkapps.ui.main.di

import com.hk.hulkapps.ui.main.di.MainModule
import com.hk.hulkapps.ui.main.MainFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }
    fun inject(fragment: MainFragment)

}