package com.example.github.igenius.di

import com.example.github.igenius.githubrepositories.data.local.FakeLocalDataSource
import com.example.github.igenius.githubrepositories.data.local.ILocalDataSource
import com.example.github.igenius.githubrepositories.data.remote.FakeRemoteDataSource
import com.example.github.igenius.githubrepositories.data.remote.IRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
object TestDataModule {

        val localDataSource: FakeLocalDataSource = FakeLocalDataSource()
        val remoteDataSource: FakeRemoteDataSource = FakeRemoteDataSource()

        // Makes Dagger provide ProjectsLocalRepository when a ProjectDataSource type is requested
        @Provides
        fun provideProjectsLocal(): ILocalDataSource {
                return localDataSource
        }

        @Provides
        fun provideProjectsRemote(): IRemoteDataSource {
                return remoteDataSource
        }

}