package com.example.github.igenius.repositorieslist

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.github.igenius.R
import com.example.github.igenius.TestGithubApplication
import com.example.github.igenius.di.TestDataModule
import com.example.github.igenius.githubrepositories.RepositoriesActivity
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsEqual
import org.junit.Test
import org.junit.runner.RunWith

//UI Testing
@RunWith(AndroidJUnit4::class)
@MediumTest
class RepositoriesListFragmentTest {

    @ExperimentalCoroutinesApi
    @Test
    fun runApp() {
        ActivityScenario.launch(RepositoriesActivity::class.java)

        // There should be in no data
        Espresso.onView(withId(R.id.appbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.github))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.noDataTextView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    private lateinit var app: TestGithubApplication

    @ExperimentalCoroutinesApi
    @Test
    fun runAppWithData() = runBlockingTest {

        //add data
        for (i in 1..10) {
            TestDataModule.localDataSource.saveRepo(
                RepositoryDTO(
                    "repo $i", "repo description $i",
                    "Kotlin", i, i % 2 == 0
                )
            )
            if (i % 2 == 0) {
                TestDataModule.remoteDataSource.setRepositoryStarred("repo $i")
            }
        }

        ActivityScenario.launch(RepositoriesActivity::class.java)

        // There should be in data
        Espresso.onView(withId(R.id.appbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.noDataTextView))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        Espresso.onView(withIndex(withId(R.id.repositoriesCardView), 0))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //check bottom sheet dialog public repo
        Espresso.onView(withIndex(withId(R.id.repositoriesCardView), 0)).perform(click())
        Espresso.onView(withId(R.id.avatar_photo))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.username))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.repo_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.repo_description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.private_repo_img))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.private_repo_text))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.custom_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //check star/starred button
        Espresso.onView(withIndex(withId(R.id.custom_button), 0)).perform(click())
        MatcherAssert.assertThat(
            TestDataModule.remoteDataSource.getRepositoryStarred("repo 1"),
            IsEqual(Result.Success(true))
        )
        Espresso.pressBack()

        //check bottom sheet dialog with private repo
        Espresso.onView(withIndex(withId(R.id.repositoriesCardView), 1)).perform(click())
        Espresso.onView(withId(R.id.avatar_photo))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.username))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.repo_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.repo_description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.private_repo_img))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.private_repo_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.custom_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //check star/starred button
        Espresso.onView(withIndex(withId(R.id.custom_button), 0)).perform(click())
        MatcherAssert.assertThat(
            TestDataModule.remoteDataSource.getRepositoryStarred("repo 1"),
            IsEqual(Result.Success(true))
        )
    }

    //https://stackoverflow.com/questions/29378552/in-espresso-how-to-avoid-ambiguousviewmatcherexception-when-multiple-views-matc
    fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            var currentIndex = 0
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}