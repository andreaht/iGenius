package com.example.github.igenius.githubrepositories.data.remote

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import javax.inject.Inject

class FakeRemoteDataSource @Inject constructor(): IRemoteDataSource {

    private val repositories = mutableListOf<RepositoryDTO>()
    private val starredRepos = mutableListOf<String>()

    override suspend fun getRepos(): Result<List<RepositoryDTO>> {
        return Result.Success(repositories)
    }

    override suspend fun getRepositoryStarred(repositoryName: String): Result<Boolean> {
        val starred = starredRepos.filter { it == repositoryName }
        return Result.Success(starred.isNotEmpty())
    }

    override suspend fun setRepositoryStarred(repositoryName: String): Result<Boolean> {
        starredRepos.add(repositoryName)
        return Result.Success(true)
    }

    override suspend fun setRepositoryNotStarred(repositoryName: String): Result<Boolean> {

        return Result.Success(starredRepos.remove(repositoryName))
    }


    fun saveRepo(repository: RepositoryDTO) {
        repositories.add(repository)
    }
}