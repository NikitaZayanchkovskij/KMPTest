package com.nikita_zayanchkovskij.userstestforkmp.data.repositories

import com.nikita_zayanchkovskij.userstestforkmp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.userstestforkmp.domain.models.UserItemModel
import com.nikita_zayanchkovskij.userstestforkmp.domain.repositories.IUsersRepositoryDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUsersRepository : IUsersRepositoryDefinition {
    private val users = mutableListOf<UserItemModel>()

    override suspend fun getUsers(loadFromRemoteApi: Boolean): Flow<NetworkResponseManager<List<UserItemModel>>> {
        return flow {
            emit(NetworkResponseManager.Loading(isLoading = true))
            emit(NetworkResponseManager.Success(data = users))
            emit(NetworkResponseManager.Loading(isLoading = false))
        }
    }

    override suspend fun getUserItemFromCacheById(userItemId: Int): Flow<UserItemModel> {
        return flow {
            users.find { user ->
                user.roomId == userItemId
            }?.let {
                emit(it)
            }
        }
    }

    /* При передачи пользователя из UsersRepositoryIImplementationTest у каких-то
     * isInBookmarks будет true, у каких-то нет, поэтому здесь ничего не трогаю.
     */
    override suspend fun insertUserItemToBookmarks(userItem: UserItemModel) {
        users.add(userItem)
    }

    override suspend fun deleteUserItemFromBookmarks(userItem: UserItemModel) {
        users.set(
            index = userItem.roomId!!,
            element = userItem.copy(isInBookmarks = false)
        )
    }

    override suspend fun getUsersInBookmarks(): Flow<List<UserItemModel>> {
        return flow {
            val usersInBookmarks = mutableListOf<UserItemModel>()
            users.forEach { user ->
                if (user.isInBookmarks == true) usersInBookmarks.add(user)
            }
            emit(usersInBookmarks)
        }
    }
}