package com.nikita_zayanchkovskij.userstestforkmp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.bookmarksScreen.BookmarksScreen
import com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.usersListScreen.UsersListScreen

@Composable
fun BottomNavigationGraph(
    padding: PaddingValues,
    bottomNavController: NavHostController,
    onNavigate: (UsersAppNavDestinations.DetailedUserItem) -> Unit
) {
    NavHost(navController = bottomNavController, startDestination = UsersAppNavDestinations.Home) {
        composable<UsersAppNavDestinations.Home> {
            UsersListScreen(bottomNavPadding = padding) { userItemId ->
                onNavigate(
                    UsersAppNavDestinations.DetailedUserItem(
                        userItemDataBaseId = userItemId
                    )
                )
            }
        }
        composable<UsersAppNavDestinations.Bookmarks> {
            BookmarksScreen(bottomNavPadding = padding) { userDataBaseId ->
                onNavigate(
                    UsersAppNavDestinations.DetailedUserItem(
                        userItemDataBaseId = userDataBaseId
                    )
                )
            }
        }
    }
}