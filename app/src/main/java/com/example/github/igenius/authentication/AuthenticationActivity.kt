package com.example.github.igenius.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.github.igenius.R
import com.example.github.igenius.databinding.ActivityAuthenticationBinding
import com.example.github.igenius.githubprojects.ProjectsActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
class AuthenticationActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AuthenticationActivity"
        const val SIGN_IN_REQUEST_CODE = 1001
    }

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Implement the create account and sign in using FirebaseUI, use sign in using email and sign in using Google
        binding.authButton.setOnClickListener { launchSignInFlow() }

        val requestCode = intent.getIntExtra("requestCode", ProjectsActivity.SIGN_IN_REQUEST_CODE)
        if(requestCode == ProjectsActivity.SIGN_OUT_REQUEST_CODE) {
            AuthUI.getInstance().signOut(this)
        }

        //enables authomatic sign in if user is already signed in
        if(FirebaseAuth.getInstance().currentUser != null)
            launchSignInFlow()

    }



    /**
     * got this function from the udacity firebase course
     */
    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_github)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }

    /**
     * got this function from the udacity firebase course
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Timber.i("Successfully signed in user " + FirebaseAuth.getInstance().currentUser?.displayName + "!")
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Timber.i("Sign in unsuccessful " + response?.error?.errorCode)
            }
        }
        finish()
    }

   override fun onBackPressed() {
        finishAffinity()
    }
}