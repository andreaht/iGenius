package com.example.github.igenius.githubprojects.data.local

import android.content.Context
import androidx.room.Room


/**
 * Singleton class that is used to create a project db
 */
object LocalDB {

    /**
     * static method that creates a project class and returns the DAO of the project
     */
    fun createProjectsDao(context: Context): ProjectsDao {
        return Room.databaseBuilder(
            context.applicationContext,
            ProjectsDatabase::class.java, "githubprojects.db"
        ).build().projectDao()
    }

}