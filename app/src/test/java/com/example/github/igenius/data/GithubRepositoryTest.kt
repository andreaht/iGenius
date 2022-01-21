package com.example.github.igenius.data


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.github.igenius.githubrepositories.data.GithubRepository
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import com.example.github.igenius.githubrepositories.data.local.FakeLocalDataSource
import com.example.github.igenius.githubrepositories.data.remote.FakeRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class GithubRepositoryTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val repos = mutableListOf<RepositoryDTO>()
    private lateinit var githubRepository: GithubRepository
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource

    @Before
    fun setup() {

        for (i in 1..10)
            repos.add(
                RepositoryDTO(
                    "repo $i", "repo description $i",
                    "Kotlin", i, i % 2 == 0
                )
            )
        localDataSource = FakeLocalDataSource()
        remoteDataSource = FakeRemoteDataSource()
        githubRepository = GithubRepository(localDataSource, remoteDataSource)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get local repository works as expected`() = runBlockingTest {

        repos.forEach { (localDataSource.saveRepo(it)) }

        val result = githubRepository.getRepos()

        when (result) {
            is Result.Success<*> -> MatcherAssert.assertThat(
                (result.data as List<RepositoryDTO>),
                IsEqual(repos)
            )
            is Result.Error -> AssertionError()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Save repository works as expected`() = runBlockingTest {

        repos.forEach { (githubRepository.saveRepo(it)) }

        val result = githubRepository.getRepos()

        when (result) {
            is Result.Success<*> -> MatcherAssert.assertThat(
                (result.data as List<RepositoryDTO>),
                IsEqual(repos)
            )
            is Result.Error -> AssertionError()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get remote repository works as expected`() = runBlockingTest {

        repos.forEach { (remoteDataSource.saveRepo(it)) }

        val result = githubRepository.getRemoteRepos()

        when (result) {
            is Result.Success<*> -> MatcherAssert.assertThat(
                (result.data as List<RepositoryDTO>),
                IsEqual(repos)
            )
            is Result.Error -> AssertionError()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get repository starred works as expected`() = runBlockingTest {

        repos.forEach { (remoteDataSource.saveRepo(it)) }

        repos.forEach {
            val result = githubRepository.getRepositoryStarred(it.name)

            when (result) {
                is Result.Success<*> -> MatcherAssert.assertThat(
                    (result.data as Boolean),
                    IsEqual(false)
                )
                is Result.Error -> AssertionError()
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Star and Unstar repository works as expected`() = runBlockingTest {

        repos.forEach { (remoteDataSource.saveRepo(it)) }

        repos.forEach { githubRepository.setRepositoryStarred(it.name)}
        repos.filter { it.star!! < 5 }.forEach{githubRepository.setRepositoryNotStarred(it.name)}

        repos.forEach {
            val result = githubRepository.getRepositoryStarred(it.name)

            when (result) {
                is Result.Success<*> -> MatcherAssert.assertThat(
                    (result.data as Boolean),
                    IsEqual(it.star!! >= 5)
                )
                is Result.Error -> AssertionError()
            }
        }
    }
}