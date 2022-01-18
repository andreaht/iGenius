package com.example.github.igenius.githubrepositories.data.local

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
/**
 * Main entry point for accessing projects data.
 */
interface ILocalDataSource {
    suspend fun getRepos(): Result<List<RepositoryDTO>>
    suspend fun saveRepo(repository: RepositoryDTO)
    suspend fun getRepo(name: String): Result<RepositoryDTO>
    suspend fun deleteAllRepos()
}