package com.example.github.igenius.githubrepositories.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO

/**
 * The Room Database that contains the projects table.
 */
@Database(entities = [RepositoryDTO::class], version = 1, exportSchema = false)
abstract class RepositoriesDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoriesDao
}