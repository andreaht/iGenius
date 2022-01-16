package com.example.github.igenius.githubrepositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.github.igenius.authentication.FirebaseUserLiveData
import javax.inject.Inject

class RepositoriesViewModel @Inject constructor() : ViewModel() {

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