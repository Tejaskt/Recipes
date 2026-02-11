package com.example.recipes.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val RECIPE_DETAIL = "recipe_detail"

}

sealed class RootRoute(val route: String){
    object Auth : RootRoute("auth")
    object Main : RootRoute("main")
}


sealed class BottomRoute(
    val route: String,
    val icon : ImageVector,
    val label : String
){
    object Home : BottomRoute("home",Icons.Outlined.Home,"Home")
    object Search : BottomRoute("search",Icons.Outlined.Search,"Search")
    object Favorites : BottomRoute("favorites",Icons.Outlined.FavoriteBorder,"Favorites")
    object Profile : BottomRoute("profile",Icons.Outlined.Person,"Profile")
}