package com.example.github.igenius.githubrepositories.data

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import com.example.github.igenius.githubrepositories.data.local.ILocalDataSource
import com.example.github.igenius.githubrepositories.data.remote.IRemoteDataSource
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource,
) {

    /**
     * Get the projects list from the local db
     * @return Result the holds a Success with all the projects or an Error object with the error message
     */
    suspend fun getProjects(): Result<List<RepositoryDTO>> {
        return localDataSource.getProjects()
    }

    suspend fun getRemoteProjects(): Result<List<RepositoryDTO>> {
        return remoteDataSource.getProjects()
    }
}