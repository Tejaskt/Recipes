package com.example.recipes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.example.recipes.ui.navigation.AppNavGraph
import com.example.recipes.ui.theme.RecipesTheme
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint


@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // for facebook login facebook sdk
    private lateinit var callbackManager: CallbackManager

    // for google login through google sdk
    private lateinit var googleSignInClient: GoogleSignInClient

    // to invoke function on successful login and navigate.
    private var onGoogleResult: ((String, String) -> Unit)? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // facebook login
        callbackManager = CallbackManager.Factory.create()

        // google sign in oauth instance
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            //.requestIdToken(getString(R.string.gcp_id))
            //.requestId()
            //.requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // main screen content.
        setContent {
            RecipesTheme {

                // passing launcher
                AppNavGraph(
                    callbackManager = callbackManager,
                    googleSignInClient = googleSignInClient,
                    googleSignInLauncher = googleSignInLauncher,
                    setGoogleResultListener = { listener ->
                        onGoogleResult = listener
                    },

                )
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }


    // Activity Result Api For google signIn
    private val googleSignInLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {

                val account = task.getResult(ApiException::class.java)
                val name = account.displayName ?: "john"
                val email = account.email ?: "johndoe@gmail.com"

                onGoogleResult?.invoke(name, email)

            } catch (e: ApiException) {

                onGoogleResult?.invoke("ERROR", "$e")

            }
        }


}
