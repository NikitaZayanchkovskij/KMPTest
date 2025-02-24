package com.nikita_zayanchkovskij.userstestforkmp.domain.repositories

import com.nikita_zayanchkovskij.userstestforkmp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.userstestforkmp.domain.models.UserItemModel
import kotlinx.coroutines.flow.Flow

interface IUsersRepositoryDefinition {
    suspend fun getUsers(loadFromRemoteApi: Boolean): Flow<NetworkResponseManager<List<UserItemModel>>>
    suspend fun getUserItemFromCacheById(userItemId: Int): Flow<UserItemModel>
    suspend fun insertUserItemToBookmarks(userItem: UserItemModel)
    suspend fun deleteUserItemFromBookmarks(userItem: UserItemModel)
    suspend fun getUsersInBookmarks(): Flow<List<UserItemModel>>
}