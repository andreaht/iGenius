package com.example.github.igenius.githubrepositories.data.local

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
/**
 * Main entry point for accessing projects data.
 */
interface ILocalDataSource {
    suspend fun getProjects(): Result<List<RepositoryDTO>>
    suspend fun saveProject(repository: RepositoryDTO)
    suspend fun getProject(id: String): Result<RepositoryDTO>
    suspend fun deleteAllProjects()
}