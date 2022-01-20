package com.example.github.igenius.githubrepositories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.github.igenius.GithubApplication
import com.example.github.igenius.databinding.ActivityRepositoriesBinding
import timber.log.Timber
import javax.inject.Inject

/**
 * The RepositoriesActivity that holds the repository fragments
 */
class RepositoriesActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: RepositoriesViewModel

    private lateinit var binding: ActivityRepositoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as GithubApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeAuthenticationState()

    }

    /**
     * Observes the authentication state and changes the UI accordingly.
     * If there is a logged in user: (1) show a logout button and (2) display their name.
     * If there is no logged in user: show a login button
     */
    private fun observeAuthenticationState() {

        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                RepositoriesViewModel.AuthenticationState.AUTHENTICATED -> {
                    Timber.i("Sign in successful")
                }
                else -> {
                    Timber.i("Sign out successful")
                }
            }
        })
    }
}
