package com.example.github.igenius.githubrepositories.data.local

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import com.example.github.igenius.githubrepository.data.local.RepositoriesDao
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
     * Get the projects list from the local db
     * @return Result the holds a Success with all the projects or an Error object with the error message
     */
    override suspend fun getProjects(): Result<List<RepositoryDTO>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(repositoriesDao.getRepositories())
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

        /**
         * Insert a project in the db.
         * @param repository the project to be inserted
         */
    override suspend fun saveProject(repository: RepositoryDTO) =
            withContext(ioDispatcher) {
                repositoriesDao.saveProject(repository)
            }

    /**
     * Get a project by its id
     * @param id to be used to get the project
     * @return Result the holds a Success object with the Project or an Error object with the error message
     */
    override suspend fun getProject(id: String): Result<RepositoryDTO> = withContext(ioDispatcher) {
        try {
            val project = repositoriesDao.getProjectById(id)
            if (project != null) {
                return@withContext Result.Success(project)
            } else {
                return@withContext Result.Error("Project not found!")
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e.localizedMessage)
        }
    }

    /**
     * Deletes all the projects in the db
     */
    override suspend fun deleteAllProjects() {
        withContext(ioDispatcher) {
            repositoriesDao.deleteAllProjects()
        }
    }
}