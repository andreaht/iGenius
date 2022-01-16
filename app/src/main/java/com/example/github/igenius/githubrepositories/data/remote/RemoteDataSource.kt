package com.example.github.igenius.githubrepositories.data.remote

import com.example.github.igenius.UserManager
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import java.util.ArrayList
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val userManager: UserManager) : IRemoteDataSource {

    override suspend fun getProjects(): Result<List<RepositoryDTO>> {
        return try {
            val repos = ArrayList<RepositoryDTO>()
            repos.addAll(
                GithubApi.retrofitService.getReposForUser(userManager.username)
            )
            Result.Success(repos)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }
}