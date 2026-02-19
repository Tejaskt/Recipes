package com.example.recipes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipes.ui.screen.auth.LoginScreen
import com.example.recipes.ui.screen.recipeDetails.RecipeDetailScreen
import com.example.recipes.ui.screen.splash.SplashScreen
import com.facebook.CallbackManager

@Composable
fun AppNavGraph(
    callbackManager: CallbackManager
) {

    val navController : NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // Splash Screen
        composable(Routes.SPLASH){
            SplashScreen(
                onNavigate = { isLoggedIn ->
                    if (isLoggedIn) {
                        navController.navigate(RootRoute.Main.route) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    } else {
                        navController.navigate(RootRoute.Auth.route) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    }
                }
            )
        }

        // Auth Graph
        navigation(
            startDestination = Routes.LOGIN,
            route = RootRoute.Auth.route
        ){
            composable(Routes.LOGIN){
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(RootRoute.Main.route) {
                            popUpTo(RootRoute.Auth.route) {
                                inclusive = true
                            }
                        }
                    },
                    callbackManager = callbackManager
                )
            }
        }

        // Main Graph (Bottom Navigation)
        navigation(
            startDestination = BottomRoute.Home.route,
            route = RootRoute.Main.route
        ){

            // Main Screen Nav Graph
            composable(BottomRoute.Home.route){
                MainScreen(
                    navController = navController
                )
            }

            // Recipe Details
            composable(
                route = "${Routes.RECIPE_DETAIL}/{recipeId}",
                arguments = listOf(
                    navArgument("recipeId") { type = NavType.IntType }
                )
            ) {
                RecipeDetailScreen(
                    onBackClick = {
                        navController.popBackStack(route = BottomRoute.Home.route, inclusive = false)
                    }
                )
            }
        }
    }
}
