package com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events

sealed interface UiEvents {
    data class ShowSnackBar(val message: String): UiEvents
    data class NavigateForwardToScreen(val userDataBaseId: Int): UiEvents
    data object PopBackStack: UiEvents
}