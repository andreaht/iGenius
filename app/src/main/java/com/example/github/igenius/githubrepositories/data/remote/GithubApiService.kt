package com.example.github.igenius.githubrepositories.data.remote


import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

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
    suspend fun getReposForUser(
        @Path("user") user: String
    ): List<RepositoryDTO>
}

object GithubApi {
    val retrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }
}