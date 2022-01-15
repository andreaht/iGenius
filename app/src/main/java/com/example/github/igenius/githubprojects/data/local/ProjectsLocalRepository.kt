package com.example.github.igenius.githubprojects.data.local

import com.example.github.igenius.githubprojects.data.dto.ProjectDTO
import com.example.github.igenius.githubprojects.data.dto.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectsLocalRepository @Inject constructor(
    private val projectsDao: ProjectsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IProjectLocalDataSource {

    /**
     * Get the projects list from the local db
     * @return Result the holds a Success with all the projects or an Error object with the error message
     */
    override suspend fun getProjects(): Result<List<ProjectDTO>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(projectsDao.getProjects())
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

        /**
         * Insert a project in the db.
         * @param project the project to be inserted
         */
    override suspend fun saveProject(project: ProjectDTO) =
            withContext(ioDispatcher) {
                projectsDao.saveProject(project)
            }

    /**
     * Get a project by its id
     * @param id to be used to get the project
     * @return Result the holds a Success object with the Project or an Error object with the error message
     */
    override suspend fun getProject(id: String): Result<ProjectDTO> = withContext(ioDispatcher) {
        try {
            val project = projectsDao.getProjectById(id)
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
            projectsDao.deleteAllProjects()
        }
    }
}