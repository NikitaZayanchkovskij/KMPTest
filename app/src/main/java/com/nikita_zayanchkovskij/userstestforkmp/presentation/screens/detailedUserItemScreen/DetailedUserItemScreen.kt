package com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.detailedUserItemScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.nikita_zayanchkovskij.userstestforkmp.R
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.detailedUserItem.DetailedUserItemViewModel
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.DetailedUserItemEvents
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UiEvents
import com.nikita_zayanchkovskij.userstestforkmp.ui.theme.IDFBlue
import com.nikita_zayanchkovskij.userstestforkmp.ui.theme.IDFWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedUserItemScreen(
    viewModel: DetailedUserItemViewModel = hiltViewModel(),
    userItemId: Int,
    popBackStack: () -> Unit
) {
    val userItemState = viewModel.userItemStateForUi.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(
            event = DetailedUserItemEvents.GetUserFromBookmarks(
                userItemDataBaseId = userItemId
            )
        )
        viewModel.uiEventToReceive.collect { uiEvent ->
            when (uiEvent) {
                is UiEvents.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = uiEvent.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is UiEvents.PopBackStack -> {
                    popBackStack()
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
                    Text(text = userItemState.value.detailedUserItem?.nameAndSurname ?: "Unknown")
                },
                navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        onClick = {
                            viewModel.onEvent(event = DetailedUserItemEvents.PopBackStack)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Navigate back icon"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackData ->
                Snackbar(
                    snackbarData = snackData,
                    containerColor = IDFBlue,
                    contentColor = IDFWhite,
                    shape = RoundedCornerShape(5.dp)
                )
            }
        }
    ) { paddingValues ->

        if (userItemState.value.detailedUserItem != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = 5.dp, end = 5.dp
                    )
            ) {
                item {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = "Your user photo here :)",
                            contentDescription = "Your user photo here :)",
                            placeholder = painterResource(R.drawable.idf_app_logo),
                            error = painterResource(R.drawable.idf_app_logo),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(top = 5.dp, bottom = 5.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    if (userItemState.value.detailedUserItem!!.isInBookmarks == true) {
                                        viewModel.onEvent(
                                            event = DetailedUserItemEvents.DeleteUserFromBookmarks(
                                                userItem = userItemState.value.detailedUserItem!!.copy(
                                                    isInBookmarks = false
                                                )
                                            )
                                        )
                                    } else {
                                        viewModel.onEvent(
                                            event = DetailedUserItemEvents.InsertUserToBookmarks(
                                                userItem = userItemState.value.detailedUserItem!!.copy(
                                                    isInBookmarks = true
                                                )
                                            )
                                        )
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.onSecondary
                                )
                            ) {
                                if (userItemState.value.detailedUserItem!!.isInBookmarks == true) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_bookmarks_filled),
                                        contentDescription = "Icon user is in Bookmarks",
                                        tint = IDFBlue
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_bookmarks_outlined),
                                        contentDescription = "Icon user is not in Bookmarks",
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                            Text(
                                text = stringResource(R.string.add_user_to_bookmarks),
                                color = IDFBlue,
                                fontSize = 16.sp
                            )
                        }
                        Text(
                            text = "User name: ${userItemState.value.detailedUserItem!!.nameAndSurname}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "User e-mail: ${userItemState.value.detailedUserItem!!.email}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "User phone: ${userItemState.value.detailedUserItem!!.phone}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "User city: ${userItemState.value.detailedUserItem!!.city}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}