package com.example.github.igenius.githubrepositories.data.remote


import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.UserDTO
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.github.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface GithubApiService {


    @GET("/users/{user}/repos")
    suspend fun getRepositoriesForUser(
        @Header("Authorization") token: String,
        @Path("user") user: String
    ): List<RepositoryDTO>

    @GET("search/repositories")
    suspend fun getPrivateRepositoriesForUser(
        @Header("Authorization") token: String,
        @Query("q") user: String
    ): NetworkRepository

    @GET("/user")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): UserDTO

    @GET("/user/starred/{user}/{repo}")
    suspend fun getRepositoryStarred(
        @Header("Authorization") token: String,
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Response<Any>

    @PUT("/user/starred/{user}/{repo}")
    suspend fun setRepositoryStarred(
        @Header("Authorization") token: String,
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Response<Any>

    @DELETE("/user/starred/{user}/{repo}")
    suspend fun setRepositoryNotStarred(
        @Header("Authorization") token: String,
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Response<Any>
}

object GithubApi {
    val retrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }
}