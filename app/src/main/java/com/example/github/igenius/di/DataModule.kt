package com.example.github.igenius.di

import com.example.github.igenius.githubrepositories.data.local.ILocalDataSource
import com.example.github.igenius.githubrepositories.data.local.LocalDataSource
import com.example.github.igenius.githubrepositories.data.remote.IRemoteDataSource
import com.example.github.igenius.githubrepositories.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module

// Tells Dagger this is a Dagger module
// Because of @Binds, DataModule needs to be an abstract class
@Module
abstract class DataModule {

    // Makes Dagger provide ProjectsLocalRepository when a ProjectDataSource type is requested
    @Binds
    abstract fun provideProjectsLocal(data: ProjectsLocalRepository): IProjectLocalDataSource
}