package com.example.github.igenius.repositorieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.github.igenius.getOrAwaitValue
import com.example.github.igenius.githubrepositories.data.GithubRepository
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import com.example.github.igenius.githubrepositories.data.remote.asRepositoryDTO
import com.example.github.igenius.githubrepositories.repositorieslist.RepositoriesListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as whenever

@RunWith(AndroidJUnit4::class)
@SmallTest
class RepositoriesListViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val repos = mutableListOf<RepositoryDTO>()
    private lateinit var viewModel: RepositoriesListViewModel
    private lateinit var githubRepository: GithubRepository

    @Before
    fun setup() {

        for (i in 1..10)
            repos.add(
                RepositoryDTO(
                    "repo $i", "repo description $i",
                    "Kotlin", i, i % 2 == 0
                )
            )

        githubRepository = mock(GithubRepository::class.java)

        viewModel = RepositoriesListViewModel(
            ApplicationProvider.getApplicationContext(), githubRepository
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Load repository works as expected`() = runBlockingTest {

        whenever(githubRepository.getRepos()).thenReturn(Result.Success(repos))

        viewModel.loadRepositories()

        MatcherAssert.assertThat(
            viewModel.reposList.getOrAwaitValue().asRepositoryDTO(),
            IsEqual(repos)
        )
    }
}