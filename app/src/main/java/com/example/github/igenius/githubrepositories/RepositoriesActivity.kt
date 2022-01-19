package com.example.github.igenius.githubrepositories

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.github.igenius.GithubApplication
import com.example.github.igenius.R
import com.example.github.igenius.authentication.AuthenticationActivity
import com.example.github.igenius.databinding.ActivityProjectsBinding
import timber.log.Timber
import javax.inject.Inject

/**
 * The RepositoriesActivity that holds the repository fragments
 */
class RepositoriesActivity : AppCompatActivity() {

    companion object {
        const val SIGN_IN_REQUEST_CODE = 1001
        const val SIGN_OUT_REQUEST_CODE = 1002
    }

    @Inject
    lateinit var viewModel: RepositoriesViewModel

    private lateinit var binding: ActivityProjectsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as GithubApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeAuthenticationState()

        //launch login activity
        val intent = Intent(this, AuthenticationActivity::class.java)
            .putExtra("requestCode", SIGN_IN_REQUEST_CODE)
        startActivity(intent)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val navHostFragment = findNavController(R.id.nav_host_fragment)
                navHostFragment.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
