package com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.mainScreen.components

import com.nikita_zayanchkovskij.userstestforkmp.R
import com.nikita_zayanchkovskij.userstestforkmp.presentation.navigation.UsersAppNavDestinations

sealed class BottomNavTabs(
    val title: String,
    val unSelectedIconId: Int,
    val selectedIconId: Int,
    val route: UsersAppNavDestinations
) {
    data object HomeTab : BottomNavTabs(
        title = "Home",
        unSelectedIconId = R.drawable.ic_home_outlined,
        selectedIconId = R.drawable.ic_home_filled,
        route = UsersAppNavDestinations.Home
    )
    data object BookmarksTab : BottomNavTabs(
        title = "Bookmarks",
        unSelectedIconId = R.drawable.ic_bookmarks_outlined,
        selectedIconId = R.drawable.ic_bookmarks_filled,
        route = UsersAppNavDestinations.Bookmarks
    )
}