package com.example.github.igenius.githubrepositories.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

/**
 * Immutable model class for a Repository. In order to compile with Room
 *
 * @param title         title of the repository
 * @param description   description of the repository
 * @param language      main programming language of the repository
 * @param star      stars of the repository
 */

@Entity(tableName = "repository")
data class RepositoryDTO (
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "language") var language: String?,
    @ColumnInfo(name = "star") @Json(name = "watchers_count") var star: Int?
)
