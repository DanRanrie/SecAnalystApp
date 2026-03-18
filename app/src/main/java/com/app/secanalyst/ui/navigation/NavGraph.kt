package com.app.secanalyst.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.secanalyst.ui.screens.FavoritesScreen
import com.app.secanalyst.ui.screens.HomeScreen
import com.app.secanalyst.ui.screens.ProfileScreen

@Composable
fun SecAnalystNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {
        composable<Route.Home> {
            HomeScreen()
        }
        composable<Route.Favorites> {
            FavoritesScreen()
        }
        composable<Route.Profile> {
            ProfileScreen()
        }
    }
}
