package com.example.github.igenius.githubprojects.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Immutable model class for a Repository. In order to compile with Room
 *
 * @param title         title of the repository
 * @param description   description of the repository
 * @param language      main programming language of the repository
 * @param star      stars of the repository location
 */

@Entity(tableName = "projects")
data class ProjectDTO(
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "language") var language: String?,
    @ColumnInfo(name = "star") var star: Boolean?
)
