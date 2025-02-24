package com.nikita_zayanchkovskij.userstestforkmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.detailedUserItemScreen.DetailedUserItemScreen
import com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.mainScreen.MainScreen

@Composable
fun MainScreensNavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = UsersAppNavDestinations.MainScreen) {
        composable<UsersAppNavDestinations.MainScreen> {
            MainScreen(mainScreensNavHostController = navController)
        }
        composable<UsersAppNavDestinations.DetailedUserItem> { backStackEntry ->
            val detailedUserScreenDestination = backStackEntry
                .toRoute<UsersAppNavDestinations.DetailedUserItem>()
            DetailedUserItemScreen(userItemId = detailedUserScreenDestination.userItemDataBaseId) {
                navController.popBackStack()
            }
        }
    }
}