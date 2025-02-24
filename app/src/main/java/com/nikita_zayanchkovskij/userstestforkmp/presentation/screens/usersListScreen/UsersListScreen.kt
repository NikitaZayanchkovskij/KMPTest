package com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.usersListScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikita_zayanchkovskij.userstestforkmp.R
import com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.usersListScreen.components.UserListItem
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UiEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UsersListEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.usersList.UsersListViewModel
import com.nikita_zayanchkovskij.userstestforkmp.ui.theme.IDFBlue

@Composable
fun UsersListScreen(
    bottomNavPadding: PaddingValues,
    viewModel: UsersListViewModel = hiltViewModel(),
    onNavigate: (userItemDataBaseId: Int) -> Unit
) {
    val usersStateForUi = viewModel.usersStateForUi.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventToReceive.collect { uiEvent ->
            when (uiEvent) {
                is UiEvents.NavigateForwardToScreen -> {
                    onNavigate(uiEvent.userDataBaseId)
                }
                else -> {}
            }
        }
    }

    if (usersStateForUi.value.usersList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (usersStateForUi.value.isLoading == true) {
                CircularProgressIndicator(
                    modifier = Modifier.size(70.dp),
                    color = IDFBlue,
                    strokeWidth = 7.dp
                )
            }
            if (usersStateForUi.value.error != null) {
                Text(
                    text = usersStateForUi.value.error!!,
                    color = IDFBlue,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(UsersListEvents.Refresh)
                        },
                    text = stringResource(R.string.try_again),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 18.sp,
                    color = IDFBlue
                )
            }
        }
    } else {
        LazyColumn(modifier = Modifier.padding(bottom = bottomNavPadding.calculateBottomPadding())) {
            items(
                items = usersStateForUi.value.usersList,
                key = { item -> item.userId }
            ) { userItemModel ->
                UserListItem(userItem = userItemModel) { userListEvent ->
                    viewModel.onEvent(event = userListEvent)
                }
            }
        }
    }
}