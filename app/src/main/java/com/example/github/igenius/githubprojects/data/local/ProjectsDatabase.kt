package com.example.github.igenius.githubprojects.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.github.igenius.githubprojects.data.dto.ProjectDTO

/**
 * The Room Database that contains the projects table.
 */
@Database(entities = [ProjectDTO::class], version = 1, exportSchema = false)
abstract class ProjectsDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectsDao
}