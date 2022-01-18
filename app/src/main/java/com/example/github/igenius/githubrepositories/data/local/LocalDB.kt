package com.example.github.igenius.githubrepositories.data.local

import android.content.Context
import androidx.room.Room


/**
 * Singleton class that is used to create a project db
 */
object LocalDB {

    /**
     * static method that creates a project class and returns the DAO of the project
     */
    fun createRepositoriesDao(context: Context): RepositoriesDao {
        return Room.databaseBuilder(
            context.applicationContext,
            RepositoriesDatabase::class.java, "githubrepositories.db"
        ).build().repositoryDao()
    }

}