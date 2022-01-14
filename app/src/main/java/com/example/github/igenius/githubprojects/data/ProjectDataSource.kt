package com.example.github.igenius.githubprojects.data

import com.example.github.igenius.githubprojects.data.dto.ProjectDTO
import com.example.github.igenius.githubprojects.data.dto.Result
/**
 * Main entry point for accessing projects data.
 */
interface ProjectDataSource {
    suspend fun getProjects(): Result<List<ProjectDTO>>
    suspend fun saveProject(project: ProjectDTO)
    suspend fun getProject(id: String): Result<ProjectDTO>
    suspend fun deleteAllProjects()
}