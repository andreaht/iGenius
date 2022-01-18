package com.example.github.igenius.githubrepositories.data.remote

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result

interface IRemoteDataSource {

    suspend fun getRepos(): Result<List<RepositoryDTO>>
}