package com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikita_zayanchkovskij.userstestforkmp.domain.repositories.IUsersRepositoryDefinition
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UiEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UsersListEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.states.UsersStatesForUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val usersRepository: IUsersRepositoryDefinition
): ViewModel() {

    private val uiEventToSend = Channel<UiEvents>()
    val uiEventToReceive = uiEventToSend.receiveAsFlow()

    private val _usersStateForUi = MutableStateFlow(UsersStatesForUi())
    val usersStateForUi = _usersStateForUi.asStateFlow()

    init {
        getAllUsersInBookmarks()
    }

    fun onEvent(event: UsersListEvents) {
        when (event) {
            is UsersListEvents.Refresh -> {
                getAllUsersInBookmarks()
            }
            is UsersListEvents.UserItemClicked -> {
                sendUiEvent(
                    uiEvent = UiEvents.NavigateForwardToScreen(
                        userDataBaseId = event.userItemDataBaseId
                    )
                )
            }
        }
    }

    private fun getAllUsersInBookmarks() {
        viewModelScope.launch {
            usersRepository.getUsersInBookmarks().collect { usersInBookmarks ->
                _usersStateForUi.update { state ->
                    state.copy(usersList = usersInBookmarks)
                }
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvents) {
        viewModelScope.launch {
            uiEventToSend.send(uiEvent)
        }
    }
}