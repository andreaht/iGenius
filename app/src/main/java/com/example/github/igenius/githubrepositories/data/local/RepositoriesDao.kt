package com.example.github.igenius.githubrepository.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO

/**
 * Data Access Object for the repository table.
 */
@Dao
interface RepositoriesDao {
    /**
     * @return all repository.
     */
    @Query("SELECT * FROM repository")
    suspend fun getRepositories(): List<RepositoryDTO>

    /**
     * @param projectId the id of the project
     * @return the project object with the projectId
     */
    @Query("SELECT * FROM repository where name = :repoName")
    suspend fun getProjectById(repoName: String): RepositoryDTO?

    /**
     * Insert a project in the database. If the project already exists, replace it.
     *
     * @param repository the project to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProject(repository: RepositoryDTO)

    /**
     * Delete all repository.
     */
    @Query("DELETE FROM repository")
    suspend fun deleteAllProjects()

}