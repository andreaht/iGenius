package com.example.github.igenius.githubrepositories.data.local

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val repositoriesDao: RepositoriesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ILocalDataSource {

    /**
     * Get the repositories list from the local db
     * @return Result the holds a Success with all the repositories or an Error object with the error message
     */
    override suspend fun getRepos(): Result<List<RepositoryDTO>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(repositoriesDao.getRepositories())
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

    /**
     * Insert a repository in the db.
     * @param repository the repository to be inserted
     */
    override suspend fun saveRepo(repository: RepositoryDTO) =
        withContext(ioDispatcher) {
            repositoriesDao.saveRepository(repository)
        }

    /**
     * Get a repository by its name
     * @param name to be used to get the repository
     * @return Result the holds a Success object with the Repository or an Error object with the error message
     */
    override suspend fun getRepo(name: String): Result<RepositoryDTO> = withContext(ioDispatcher) {
        try {
            val repository = repositoriesDao.getRepositoryById(name)
            if (repository != null) {
                return@withContext Result.Success(repository)
            } else {
                return@withContext Result.Error("Repository not found!")
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e.localizedMessage)
        }
    }

    /**
     * Deletes all the repositories in the db
     */
    override suspend fun deleteAllRepos() {
        withContext(ioDispatcher) {
            repositoriesDao.deleteAllRepositories()
        }
    }
}