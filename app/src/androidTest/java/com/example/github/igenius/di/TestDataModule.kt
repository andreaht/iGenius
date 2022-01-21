package com.example.github.igenius.di

import com.example.github.igenius.githubrepositories.data.local.FakeLocalDataSource
import com.example.github.igenius.githubrepositories.data.local.ILocalDataSource
import com.example.github.igenius.githubrepositories.data.remote.FakeRemoteDataSource
import com.example.github.igenius.githubrepositories.data.remote.IRemoteDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class TestDataModule {

        // Makes Dagger provide ProjectsLocalRepository when a ProjectDataSource type is requested
        @Binds
        abstract fun provideProjectsLocal(data: FakeLocalDataSource): ILocalDataSource

        @Binds
        abstract fun provideProjectsRemote(data: FakeRemoteDataSource): IRemoteDataSource

}