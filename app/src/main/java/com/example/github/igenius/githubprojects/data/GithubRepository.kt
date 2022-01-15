package com.example.github.igenius.githubprojects.data

import com.example.github.igenius.githubprojects.data.local.IProjectLocalDataSource
import com.example.github.igenius.githubprojects.data.remote.IProjectRemoteDataSource
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val localDataSource: IProjectLocalDataSource,
    private val remoteDataSource: IProjectRemoteDataSource
) {  }