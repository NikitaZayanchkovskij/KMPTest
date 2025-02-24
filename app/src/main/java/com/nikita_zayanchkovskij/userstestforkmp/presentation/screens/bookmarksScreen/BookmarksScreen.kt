package com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.bookmarksScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikita_zayanchkovskij.userstestforkmp.R
import com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.usersListScreen.components.UserListItem
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.bookmarks.BookmarksViewModel
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UiEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UsersListEvents
import com.nikita_zayanchkovskij.userstestforkmp.ui.theme.IDFBlue
import com.nikita_zayanchkovskij.userstestforkmp.ui.theme.IDFWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    bottomNavPadding: PaddingValues,
    viewModel: BookmarksViewModel = hiltViewModel(),
    navigateToDetailedUserScreen: (userItemDataBaseId: Int) -> Unit
) {
    val usersStateForUi = viewModel.usersStateForUi.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(event = UsersListEvents.Refresh)
        viewModel.uiEventToReceive.collect { uiEvent ->
            when (uiEvent) {
                is UiEvents.NavigateForwardToScreen -> {
                    navigateToDetailedUserScreen(uiEvent.userDataBaseId)
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    //containerColor = MaterialTheme.colorScheme.background,
                    containerColor = IDFBlue,
                    //titleContentColor = MaterialTheme.colorScheme.onBackground
                    titleContentColor = IDFWhite
                ),
                title = {
                    Text(text = stringResource(R.string.bookmarks))
                }
            )
        }
    ) { paddingValues ->

        if (usersStateForUi.value.usersList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.no_bookmarks_yet))
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = bottomNavPadding.calculateBottomPadding()
                )
            ) {
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
}