package com.nikita_zayanchkovskij.userstestforkmp.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface UsersAppNavDestinations {
    @Serializable
    data object MainScreen : UsersAppNavDestinations
    @Serializable
    data object Home : UsersAppNavDestinations
    @Serializable
    data object Bookmarks : UsersAppNavDestinations
    @Serializable
    data class DetailedUserItem(val userItemDataBaseId: Int): UsersAppNavDestinations
}