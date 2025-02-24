package com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.detailedUserItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikita_zayanchkovskij.userstestforkmp.domain.repositories.IUsersRepositoryDefinition
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.DetailedUserItemEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UiEvents
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
class DetailedUserItemViewModel @Inject constructor(
    private val usersRepository: IUsersRepositoryDefinition
) : ViewModel() {

    private val uiEventToSend = Channel<UiEvents>()
    val uiEventToReceive = uiEventToSend.receiveAsFlow()

    private val _userItemStateForUi = MutableStateFlow(UsersStatesForUi())
    val userItemStateForUi = _userItemStateForUi.asStateFlow()

    fun onEvent(event: DetailedUserItemEvents) {
        when (event) {
            is DetailedUserItemEvents.InsertUserToBookmarks -> {
                viewModelScope.launch {
                    usersRepository.insertUserItemToBookmarks(userItem = event.userItem)
                }.invokeOnCompletion {
                    getUserItemFromDataBaseById(userItemId = event.userItem.roomId!!)
                    sendUiEvent(
                        uiEvent = UiEvents.ShowSnackBar(
                            message = "User was Added to Bookmarks."
                        )
                    )
                }
            }
            is DetailedUserItemEvents.DeleteUserFromBookmarks -> {
                viewModelScope.launch {
                    usersRepository.deleteUserItemFromBookmarks(userItem = event.userItem)
                }.invokeOnCompletion {
                    getUserItemFromDataBaseById(userItemId = event.userItem.roomId!!)
                    sendUiEvent(
                        uiEvent = UiEvents.ShowSnackBar(
                            message = "User was Removed from Bookmarks."
                        )
                    )
                }
            }
            is DetailedUserItemEvents.GetUserFromBookmarks -> {
                getUserItemFromDataBaseById(userItemId = event.userItemDataBaseId)
            }
            is DetailedUserItemEvents.PopBackStack -> {
                sendUiEvent(UiEvents.PopBackStack)
            }
        }
    }

    private fun getUserItemFromDataBaseById(userItemId: Int) {
        viewModelScope.launch {
            usersRepository.getUserItemFromCacheById(userItemId).collect { userItemModel ->
                _userItemStateForUi.update { state ->
                    state.copy(detailedUserItem = userItemModel)
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