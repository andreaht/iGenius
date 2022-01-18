package com.example.github.igenius.githubrepositories.data.local

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
     * @param repoName the name of the repository
     * @return the repository object with the repositoryId
     */
    @Query("SELECT * FROM repository where name = :repoName")
    suspend fun getRepositoryById(repoName: String): RepositoryDTO?

    /**
     * Insert a repository in the database. If the repository already exists, replace it.
     *
     * @param repository the repository to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRepository(repository: RepositoryDTO)

    /**
     * Delete all repository.
     */
    @Query("DELETE FROM repository")
    suspend fun deleteAllRepositories()

}