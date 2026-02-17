package com.example.recipes.ui.screen.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.R
import com.example.recipes.ui.theme.CreamBackground
import com.example.recipes.ui.theme.RecipesTheme
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

@SuppressLint("ContextCastToActivity")
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
    callbackManager: CallbackManager
) {
    val context = LocalContext.current
    val activity = context as Activity
    val state by viewModel.uiState.collectAsState()
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (val authState = authState) {

            is AuthUiState.Success -> {
                Log.d("FB_USER", "Name: ${authState.user.name}")
                Log.d("FB_USER", "Email: ${authState.user.email}")
                onLoginSuccess()
            }

            is AuthUiState.Error -> {
               //
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {

        LoginManager.getInstance().registerCallback(

            callbackManager,
            callback = object : FacebookCallback<LoginResult> {

                override fun onSuccess(result: LoginResult) {

                    val request = GraphRequest.newMeRequest(
                        result.accessToken
                    ) { obj, _ ->

                        if (obj != null) {
                            val name = obj.optString("name")
                            val email = obj.optString("email")
                            val id = obj.optString("id")

                            val profilePicture =
                                "https://graph.facebook.com/$id/picture?type=large"

                            viewModel.onFacebookUserFetched(
                                name = name,
                                email = email
                            )
                        } else {
                            viewModel.onFacebookError("Failed to fetch profile")
                        }
                    }

                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "id,name,email"
                    )
                    request.parameters = parameters
                    request.executeAsync()
                }


                override fun onCancel() {
                    viewModel.onFacebookError("Login Cancelled")
                }

                override fun onError(error: FacebookException) {
                    viewModel.onFacebookError(error.message ?: "Unknown Error")
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.displayCutout),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(24.dp)

    ) {
        item {
            Icon(
                painter = painterResource(R.drawable.chef_hat),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(75.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.tertiary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                stringResource(R.string.recipify),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.tagLine),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))


            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                placeholder = { Text(stringResource(R.string.email_address)) },
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                shape = RoundedCornerShape(18.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.login(onLoginSuccess) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(18.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp), strokeWidth = 2.dp
                    )
                } else {
                    Text(stringResource(R.string.sign_in))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.don_t_have_an_account),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier =  Modifier.height(20.dp))

            /*-- FACEBOOK LOGIN--*/
            Button(
                onClick = {
                    LoginManager.getInstance().logInWithReadPermissions(
                        activity,
                        listOf("email", "public_profile")
                    )
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.AutoMirrored.Outlined.Login,null)
                    Text("Continue with Facebook")
                }
            }

            //FacebookLoginButton()

            if(authState is AuthUiState.Error){
                Spacer(Modifier.height(12.dp))
                Text(
                    text = (authState as AuthUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }


            state.error?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun FacebookLoginButton() {

    val context = LocalContext.current
    val activity = context as Activity
    val callbackManager = remember { CallbackManager.Factory.create() }

    val loginManager = LoginManager.getInstance()

    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult>{

                override fun onSuccess(result: LoginResult) {
                    val accessToken = result.accessToken
                    Log.d("FB_LOGIN", "Token: ${accessToken.token}")
                }

                override fun onCancel() {
                    Log.d("FB_LOGIN", "Cancelled")
                }


                override fun onError(error: FacebookException) {
                    Log.e("FB_LOGIN", error.message ?: "")
                }
            })
        onDispose {}
    }

    Button(onClick = {
        loginManager.logInWithReadPermissions(
            activity,
            listOf("email", "public_profile")
        )
    }) {
        Text("Continue with Facebook")
    }
}


@Preview
@Composable
private fun PrevLogin() {
    RecipesTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamBackground),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Icon(
                    painter = painterResource(R.drawable.chef_hat),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(75.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.extraLarge
                        )
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Recipify", style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Your Personal recipe companion",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Email Address") },
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(0.8f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.8f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Password") },
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(0.8f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.8f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(18.dp)

                ) {
                    Text("Sign In")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }
    }
}