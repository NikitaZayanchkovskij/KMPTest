package com.nikita_zayanchkovskij.userstestforkmp.presentation.screens.usersListScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nikita_zayanchkovskij.userstestforkmp.R
import com.nikita_zayanchkovskij.userstestforkmp.domain.models.UserItemModel
import com.nikita_zayanchkovskij.userstestforkmp.presentation.viewModels.events.UsersListEvents
import com.nikita_zayanchkovskij.userstestforkmp.ui.theme.IDFBlue

@Composable
fun UserListItem(
    userItem: UserItemModel,
    onEvent: (UsersListEvents) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, top = 5.dp, end = 5.dp)
            .clickable {
                onEvent(
                    UsersListEvents.UserItemClicked(
                        userItemDataBaseId = userItem.roomId ?: 0
                    )
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "Your user photo here :)",
                contentDescription = "Your user photo here :)",
                placeholder = painterResource(R.drawable.idf_app_logo),
                error = painterResource(R.drawable.idf_app_logo),
                modifier = Modifier
                    .size(150.dp)
                    .padding(start = 3.dp, top = 3.dp, bottom = 3.dp, end = 5.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(end = 5.dp)) {
                Text(
                    text = "User id: ${userItem.userId}",
                    color = IDFBlue,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "User name: ${userItem.nameAndSurname}",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "User e-mail: ${userItem.email}",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}