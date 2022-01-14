package com.example.github.igenius

import android.app.Application
import com.example.github.igenius.di.AppComponent
import com.example.github.igenius.di.DaggerAppComponent
import dagger.Component
import timber.log.Timber

// Definition of the Application graph
@Component
interface ApplicationComponent {  }

// appComponent lives in the Application class to share its lifecycle
class GithubProjectsApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(this)
    }


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}