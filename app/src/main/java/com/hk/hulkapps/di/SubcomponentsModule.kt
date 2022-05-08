package com.hk.hulkapps.di

import com.hk.hulkapps.ui.main.di.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        MainComponent::class
    ]
)
class SubcomponentsModule