package com.example.github.igenius.di

import android.app.Application
import com.example.github.igenius.githubrepositories.data.local.LocalDB
import com.example.github.igenius.githubrepositories.data.local.RepositoriesDao
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


// Tells Dagger this is a Dagger module
// Because of @Binds, DataModule needs to be an abstract class
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideLoginDao(app: Application): RepositoriesDao {
        return LocalDB.createRepositoriesDao(app)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}