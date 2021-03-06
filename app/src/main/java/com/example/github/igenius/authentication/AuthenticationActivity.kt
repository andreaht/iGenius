package com.example.github.igenius.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.github.igenius.GithubApplication
import com.example.github.igenius.R
import com.example.github.igenius.UserManager
import com.example.github.igenius.databinding.ActivityAuthenticationBinding
import com.example.github.igenius.githubrepositories.RepositoriesActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber
import javax.inject.Inject

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RepositoriesActivity.
 */
class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {

        // Ask Dagger to inject our dependencies
        (application as GithubApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Implement the create account and sign in using FirebaseUI, use sign in using email and sign in using Google
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            firebaseLoginResult(result.resultCode, result.data)
        }

        //enables automatic sign in if user is already signed in
        if(FirebaseAuth.getInstance().currentUser != null) {
            launchSignInFlow(resultLauncher)
        }

        //change login to logout button according to the firebase auth state
        FirebaseAuth.getInstance().addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null)
                binding.authButton.setText(R.string.login)
            else
                binding.authButton.setText(R.string.logout)
        }

        //handle signin/signout button clicks
        binding.authButton.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                Firebase.auth.signOut()
            }else
                launchSignInFlow(resultLauncher)
        }

    }

    private fun launchSignInFlow(resultLauncher: ActivityResultLauncher<Intent>) {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val scopes = listOf("repo")
        val providers = arrayListOf(
            AuthUI.IdpConfig.GitHubBuilder().setScopes(scopes).build()
        )

        resultLauncher.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_github)
                .build()
        )
    }

    private fun firebaseLoginResult(resultCode: Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)
        if (resultCode == Activity.RESULT_OK) {
            // User successfully signed in
            Timber.i("Successfully signed in user " + FirebaseAuth.getInstance().currentUser?.displayName + "!")
            Timber.i("Successfully signed in token " + response?.idpToken)
            userManager.displayName = FirebaseAuth.getInstance().currentUser?.displayName.toString()
            userManager.uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
            userManager.token = response?.idpToken ?: ""

            binding.authButton.setText(R.string.logout)

            //launch repository activity
            val intent = Intent(this, RepositoriesActivity::class.java)
            startActivity(intent)

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            Timber.i("Sign in unsuccessful " + response?.error?.errorCode)
        }
    }
}
