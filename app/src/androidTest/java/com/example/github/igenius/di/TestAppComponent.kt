package com.example.github.igenius.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// Replacement for AppComponent in android tests
@Singleton
// Includes TestStorageModule that overrides objects provided in StorageModule
@Component(modules = [TestDataModule::class, RoomModule::class, TestUserManagerModule::class])
interface TestAppComponent : AppComponent {
    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Application passed in will be available in the graph
        fun create(@BindsInstance application: Application): AppComponent
    }
}