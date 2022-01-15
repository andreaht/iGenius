package com.example.github.igenius.githubprojects.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.github.igenius.githubprojects.data.dto.ProjectDTO

/**
 * Data Access Object for the projects table.
 */
@Dao
interface ProjectsDao {
    /**
     * @return all projects.
     */
    @Query("SELECT * FROM projects")
    suspend fun getProjects(): List<ProjectDTO>

    /**
     * @param projectId the id of the project
     * @return the project object with the projectId
     */
    @Query("SELECT * FROM projects where entry_id = :projectId")
    suspend fun getProjectById(projectId: String): ProjectDTO?

    /**
     * Insert a project in the database. If the project already exists, replace it.
     *
     * @param project the project to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProject(project: ProjectDTO)

    /**
     * Delete all projects.
     */
    @Query("DELETE FROM projects")
    suspend fun deleteAllProjects()

}