package com.example.recipes.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipes.ui.screen.favorites.FavoriteScreen
import com.example.recipes.ui.screen.profile.ProfileScreen
import com.example.recipes.ui.screen.recipes.RecipeListScreen
import com.example.recipes.ui.screen.search.SearchScreen

@Composable
fun MainScreen (
    navController: NavHostController
){

    // Separate Bottom nav stack
    val bottomNavController = rememberNavController()

    // Bottom navbar Items
    val items = listOf(BottomRoute.Home,BottomRoute.Search,BottomRoute.Favorites,BottomRoute.Profile)

    val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            bottomNavController.navigate(item.route){
                                popUpTo(bottomNavController.graph.startDestinationId){
                                    saveState  = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(item.icon,null)
                        },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { paddingValues ->

        NavHost(
            navController = bottomNavController,
            startDestination = BottomRoute.Home.route,
            modifier = Modifier.padding(paddingValues)
        ){

            // Recipe List Screen
            composable(BottomRoute.Home.route){
                RecipeListScreen(
                    onRecipeItemClick = { id ->
                        navController.navigate("${Routes.RECIPE_DETAIL}/$id")
                    }
                )
            }

            // Search Screen
            composable(BottomRoute.Search.route){
                SearchScreen(onRecipeClick =  { id ->
                    navController.navigate("${Routes.RECIPE_DETAIL}/$id")
                })
            }

            // Favorite Screen
            composable(BottomRoute.Favorites.route){
                FavoriteScreen(
                    onRecipeItemClick = { id ->
                        navController.navigate("${Routes.RECIPE_DETAIL}/$id")
                    }
                )
            }

            // Profile Screen
            composable(BottomRoute.Profile.route){
                ProfileScreen(
                    onLogout = {
                        navController.navigate(RootRoute.Auth.route) {
                            popUpTo(RootRoute.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

    }
}