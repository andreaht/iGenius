package com.example.github.igenius.githubrepositories.data

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import com.example.github.igenius.githubrepositories.data.local.ILocalDataSource
import com.example.github.igenius.githubrepositories.data.remote.IRemoteDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource,
) {

    /**
     * Get the projects list from the local db
     * @return Result the holds a Success with all the projects or an Error object with the error message
     */
    suspend fun getRepos(): Result<List<RepositoryDTO>> {
        return localDataSource.getRepos()
    }

    /**
     * Insert a repository in the db.
     * @param repository the repository to be inserted
     */
    suspend fun saveRepo(repository: RepositoryDTO) {
        localDataSource.saveRepo(repository)
    }

    suspend fun getRemoteRepos(): Result<List<RepositoryDTO>> {
        return remoteDataSource.getRepos()
    }

    suspend fun getRepositoryStarred(repositoryName: String): Result<Boolean> {
        return remoteDataSource.getRepositoryStarred(repositoryName)
    }
}