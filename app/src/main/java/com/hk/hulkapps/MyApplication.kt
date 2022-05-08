package com.hk.hulkapps

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.hk.hulkapps.di.AppComponent
import com.hk.hulkapps.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class MyApplication : Application() {
    // Reference to the application graph that is used across the whole app
//    @Inject
//    lateinit var workerFactory: WorkerFactory
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
//        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())
    }
}