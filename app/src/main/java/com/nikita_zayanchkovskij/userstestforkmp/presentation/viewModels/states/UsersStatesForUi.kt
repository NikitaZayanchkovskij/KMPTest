package com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.states

import com.nikita_zayanchkovskij.userstestforkmp.domain.models.UserItemModel

data class UsersStatesForUi(
    val usersList: List<UserItemModel> = emptyList(),
    val detailedUserItem: UserItemModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
