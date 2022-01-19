package com.example.github.igenius.githubrepositories.data.remote

import com.example.github.igenius.UserManager
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import java.util.ArrayList
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val userManager: UserManager) :
    IRemoteDataSource {

    override suspend fun getRepos(): Result<List<RepositoryDTO>> {
        return try {
            if(userManager.isTokenInitialized() && !userManager.isUserInitialized())
                userManager.user = GithubApi.retrofitService.getUser("Token " + userManager.token)
            if(userManager.isUserInitialized()) {
                val repos = ArrayList<RepositoryDTO>()
                repos.addAll(
                    GithubApi.retrofitService.getReposForUser(
                        "Token " + userManager.token,
                        userManager.user.login)
                )
                Result.Success(repos)
            }
            else
                Result.Error("")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun getRepositoryStarred(repositoryName: String): Result<Boolean> {
        return try {
            if(userManager.isTokenInitialized() && !userManager.isUserInitialized())
                userManager.user = GithubApi.retrofitService.getUser("Token " + userManager.token)
            if(userManager.isUserInitialized()) {
                val response = GithubApi.retrofitService.getRepoStarred(
                    "Token " + userManager.token,
                    userManager.user.login,
                    repositoryName
                )
                return when {
                    response.code() == 204 -> Result.Success(true)
                    response.code() == 404 -> Result.Success(false)
                    else -> Result.Error(response.message())
                }
            }
            else
                Result.Error("")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }
}