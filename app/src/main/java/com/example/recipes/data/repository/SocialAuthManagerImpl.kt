package com.example.recipes.data.repository

import android.content.Intent
import android.os.Bundle
import com.example.recipes.domain.model.User
import com.example.recipes.domain.repository.SocialAuthManager
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.java

@Singleton
class SocialAuthManagerImpl @Inject constructor(
    private val googleSignInClient: GoogleSignInClient
) : SocialAuthManager {

    /*--- GOOGLE LOGIN ---*/
    override suspend fun loginWithGoogle(intent: Intent?): Result<User> {
        return try {

            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.getResult(ApiException::class.java)

            Result.success(
                User(
                    unm = account.displayName ?: "",
                    firstName = account.givenName ?: "",
                    lastName = account.familyName ?: "",
                    email = account.email ?: "",
                    image = account.photoUrl?.toString() ?: "",
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /*--- FACEBOOK LOGIN ---*/
    override suspend fun loginWithFacebook(token: AccessToken): Result<User> {
        return suspendCancellableCoroutine { continuation ->

            val request = GraphRequest.newMeRequest(token) { obj, _ ->
                if (obj != null) {

                    val pictureUrl =
                        obj.optJSONObject("picture")?.optJSONObject("data")?.optString("url")

                    val user = User(
                        unm = obj.optString("name"),
                        email = obj.optString("email"),
                        firstName = obj.optString("first_name"),
                        lastName = obj.optString("last_name"),
                        image = pictureUrl ?: ""
                    )
                    continuation.resume(Result.success(user)) { cause, _, _ -> null?.let { it(cause) } }

                } else {
                    continuation.resume(Result.failure(Exception("FB error"))) { cause, _, _ -> null?.let {it(cause)}}
                }
            }

            val parameters = Bundle()
            parameters.putString("fields", "id,first_name,last_name,name,email,picture.type(large)")
            request.parameters = parameters
            request.executeAsync()
        }
    }

    /*--- LOGOUT ---*/
    override suspend fun logout() {

        try {
            googleSignInClient.signOut()
        } catch (_: Exception) {
        }

        LoginManager.getInstance().logOut()
    }


}

