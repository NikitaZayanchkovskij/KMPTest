package com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.usersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikita_zayanchkovskij.userstestforkmp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.userstestforkmp.domain.repositories.IUsersRepositoryDefinition
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UiEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UsersListEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.states.UsersStatesForUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val usersRepository: IUsersRepositoryDefinition
) : ViewModel() {

    private val uiEventToSend = Channel<UiEvents>()
    val uiEventToReceive = uiEventToSend.receiveAsFlow()

    private val _usersStateForUi = MutableStateFlow(UsersStatesForUi())
    val usersStateForUi = _usersStateForUi.asStateFlow()

    init {
        refreshUsers()
    }

    fun onEvent(event: UsersListEvents) {
        when (event) {
            is UsersListEvents.Refresh -> {
                //refreshUsers()
            }
            is UsersListEvents.UserItemClicked -> {
                sendUiEvent(
                    event = UiEvents.NavigateForwardToScreen(
                        userDataBaseId = event.userItemDataBaseId
                    )
                )
            }
        }
    }

    private fun refreshUsers() {
        viewModelScope.launch {
            usersRepository.getUsers(loadFromRemoteApi = true).collect { response ->

                when (response) {
                    is NetworkResponseManager.Success -> {
                        response.data?.let { usersListAsModels ->
                            _usersStateForUi.update { state ->
                                state.copy(
                                    usersList = usersListAsModels,
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is NetworkResponseManager.Error -> {
                        _usersStateForUi.update { state ->
                            state.copy(
                                error = response.message,
                                isLoading = false
                            )
                        }
                    }
                    is NetworkResponseManager.Loading -> {
                        _usersStateForUi.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch {
            uiEventToSend.send(event)
        }
    }
}