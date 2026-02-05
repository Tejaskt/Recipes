package com.example.recipes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipes.ui.screen.auth.LoginScreen
import com.example.recipes.ui.screen.recipes.RecipeListScreen
import com.example.recipes.ui.screen.splash.SplashScreen

@Composable
fun AppNavGraph()
{
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigate = { isLoggedIn ->
                    navController.navigate(
                        if (isLoggedIn) Routes.RECIPE_LIST else Routes.LOGIN
                    ) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.RECIPE_LIST) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.RECIPE_LIST) {
            RecipeListScreen()
        }
    }
}
