package com.example.github.igenius.di

import com.example.github.igenius.githubprojects.data.local.IProjectLocalDataSource
import com.example.github.igenius.githubprojects.data.local.ProjectsLocalRepository
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