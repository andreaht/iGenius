package com.example.github.igenius.di

import com.example.github.igenius.UserManager
import com.example.github.igenius.githubrepositories.data.dto.UserDTO
import dagger.Module
import dagger.Provides

@Module
object TestUserManagerModule {

    private val userManager: UserManager = UserManager()

    @Provides
    fun provideUserManager(): UserManager {
        return userManager
    }

    init {
        userManager.user = UserDTO(
            gists_url = null,
            repos_url = null,
            following_url = null,
            twitter_username = null,
            bio = null,
            created_at = null,
            login = "name",
            type = null,
            blog = null,
            subscriptions_url = null,
            updated_at = null,
            site_admin = true,
            company = null,
            id = 0,
            public_repos = 0,
            gravatar_id = null,
            email = null,
            organizations_url = null,
            hireable = null,
            starred_url = null,
            followers_url = null,
            public_gists = 0,
            url = null,
            received_events_url = null,
            followers = 0,
            avatar_url = "https://github.com/twbs.png",
            events_url = null,
            html_url = null,
            following = 0,
            name = null,
            location = null,
            node_id = null,
        )
    }

}