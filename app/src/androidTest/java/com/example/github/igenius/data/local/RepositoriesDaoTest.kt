package com.example.github.igenius.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.local.RepositoriesDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RepositoriesDaoTest {

    private lateinit var database: RepositoriesDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepositoriesDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertRepositoriesAndGetByName() = runBlockingTest {
        // GIVEN - insert repositories
        for (i in 1..10)
            database.repositoryDao().saveRepository(
                RepositoryDTO(
                    "repo $i", "repo description $i",
                    "Kotlin", i, i % 2 == 0
                )
            )

        // WHEN - Get the beer from the database
        val loaded = database.repositoryDao().getRepositoryById("repo 1")

        // THEN - The loaded data contains the expected values
        MatcherAssert.assertThat(loaded , CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded?.name , IsEqual("repo 1"))

    }
}