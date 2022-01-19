package com.example.github.igenius.githubrepositories.data.remote

import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkRepository(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResult: Boolean,
    var items: List<RepositoryDTO>
)

fun NetworkRepository.asRepositoryDTO(): List<RepositoryDTO> {
    return this.items.map {
        RepositoryDTO(
            name = it.name,
            description = it.description,
            language = it.language,
            star = it.star
        )
    }
}
