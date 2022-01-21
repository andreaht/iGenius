package com.example.github.igenius.githubrepositories.data.local

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import javax.inject.Inject

class FakeLocalDataSource @Inject constructor(): ILocalDataSource {

    private val repositories = mutableListOf<RepositoryDTO>()

    override suspend fun getRepos(): Result<List<RepositoryDTO>> {
        return Result.Success(repositories)
    }

    override suspend fun saveRepo(repository: RepositoryDTO) {
        repositories.add(repository)
    }

    override suspend fun getRepo(name: String): Result<RepositoryDTO> {
        val repository = repositories.filter { it.name == name }
        return if (repository.isNotEmpty()) {
            Result.Success(repository.first())
        } else {
            Result.Error("Repository not found!")
        }
    }

    override suspend fun deleteAllRepos() {
        repositories.clear()
    }
}