package com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events

import com.nikita_zayanchkovskij.userstestforkmp.domain.models.UserItemModel

sealed interface DetailedUserItemEvents {
    data class InsertUserToBookmarks(val userItem: UserItemModel): DetailedUserItemEvents
    data class DeleteUserFromBookmarks(val userItem: UserItemModel): DetailedUserItemEvents
    data class GetUserFromBookmarks(val userItemDataBaseId: Int): DetailedUserItemEvents
    data object PopBackStack: DetailedUserItemEvents
}