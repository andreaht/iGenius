package com.example.github.igenius.githubprojects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.github.igenius.authentication.FirebaseUserLiveData
import javax.inject.Inject

class ProjectsViewModel @Inject constructor() : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}