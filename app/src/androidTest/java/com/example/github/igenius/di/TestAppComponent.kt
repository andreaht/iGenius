package com.example.github.igenius.di

import dagger.Component
import javax.inject.Singleton

// Replacement for AppComponent in android tests
@Singleton
// Includes TestStorageModule that overrides objects provided in StorageModule
@Component(modules = [TestDataModule::class, RoomModule::class])
interface TestAppComponent : AppComponent