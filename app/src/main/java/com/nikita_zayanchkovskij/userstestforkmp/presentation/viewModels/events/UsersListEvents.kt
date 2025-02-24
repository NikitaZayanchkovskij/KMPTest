package com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events

sealed interface UsersListEvents {
    data object Refresh: UsersListEvents
    data class UserItemClicked(val userItemDataBaseId: Int): UsersListEvents
}