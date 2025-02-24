package com.nikita_zayanchkovskij.userstestforkmp.data.repositories

import com.nikita_zayanchkovskij.userstestforkmp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.userstestforkmp.data.local.UsersDataBase
import com.nikita_zayanchkovskij.userstestforkmp.data.mappers.mapToUserEntity
import com.nikita_zayanchkovskij.userstestforkmp.data.mappers.mapToUserItemModel
import com.nikita_zayanchkovskij.userstestforkmp.data.remote.IJsonPlaceHolderApi
import com.nikita_zayanchkovskij.userstestforkmp.domain.models.UserItemModel
import com.nikita_zayanchkovskij.userstestforkmp.domain.repositories.IUsersRepositoryDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsersRepositoryImplementation @Inject constructor(
    private val jsonPlaceHolderApi: IJsonPlaceHolderApi,
    private val usersDataBase: UsersDataBase
) : IUsersRepositoryDefinition {

    private val usersDao = usersDataBase.usersDao

    override suspend fun getUsers(loadFromRemoteApi: Boolean): Flow<NetworkResponseManager<List<UserItemModel>>> {
        return flow {
            emit(NetworkResponseManager.Loading(isLoading = true))
            val usersInCacheAsEntities = usersDao.getAllUsersFromCache()
            emit(
                NetworkResponseManager.Success(
                    data = usersInCacheAsEntities.map { userEntity ->
                        userEntity.mapToUserItemModel()
                    }
                )
            )
            val isUsersCacheIsEmpty = usersInCacheAsEntities.isEmpty()

            if (isUsersCacheIsEmpty == false && loadFromRemoteApi == false) {
                emit(NetworkResponseManager.Loading(isLoading = false))
                return@flow
            }

            val usersListFromApiAsModels = arrayListOf<UserItemModel>()
            try {
                val response = jsonPlaceHolderApi.getUsers()
                if (response.isSuccessful) {
                    val listOfUsersAsDto = response.body()!!
                    listOfUsersAsDto.forEach { userDto ->
                        val userItemAsModel = userDto.mapToUserItemModel()
                        usersListFromApiAsModels.add(userItemAsModel)
                    }
                }

            } catch (ioException: IOException) {
                ioException.printStackTrace()
                emit(
                    NetworkResponseManager.Error(
                        message = "${ioException.message}.\nIOException." +
                                "\n Please, check your internet connection."

                    )
                )

            } catch (httpException: HttpException) {
                httpException.printStackTrace()
                emit(
                    NetworkResponseManager.Error(
                        message = "${httpException.message}.\nHttpException."
                    )
                )
            }

            /* Когда получили список пользователей с сервера: */
            if (usersListFromApiAsModels.isNotEmpty()) {

                /* Получаем всех пользователей, которые сейчас в закладках. */
                val usersInBookmarksAsEntities = usersDao.getAllUsersInBookmarks(isInBookmarks = true)
                val usersInBookmarksAsModels = usersInBookmarksAsEntities.map { userEntity ->
                    userEntity.mapToUserItemModel()
                }

                /* Каждого пользователя, который остался в закладках, сравниваю по id с пользователем
                 * с сервера, и если id совпадают - заменяю пользователя в списке с сервера.
                 *
                 * Также может быть такое, что мы например долгое время не обновляли с сервера,
                 * и потом когда обновили - пришли все пользователи новые и ни один из них не был
                 * в закладках.
                 * В таком случае нужно просто объединить два списка.
                 */
                val tempList = usersListFromApiAsModels

                usersInBookmarksAsModels.forEach { userFromBookmarks ->
                    usersListFromApiAsModels.forEachIndexed { position, userFromServer ->

                        if (userFromBookmarks.userId == userFromServer.userId) {
                            tempList.set(index = position, element = userFromBookmarks)
                        }
                    }
                }

                /* None возвращает true если ни один из элементов списка не соответствует условию
                 * т.е. ни у одного пользователя isInBookmarks не равно true.
                 * Если хоть один пользователь находится в закладках - вернёт false.
                 */
                val nothingWasInBookmarks = tempList.none { user ->
                    user.isInBookmarks == true
                }

                /* И теперь проверяем, если ни один пользователь не был в закладках -
                 * то просто объединяем два списка.
                 */
                if (nothingWasInBookmarks == true) {
                    tempList.addAll(usersInBookmarksAsModels)
                }

                /* Теперь в tempList у нас все актуальные данные: и новые, и те, что были в закладках.
                 * Поэтому теперь можно очистить вообще всё, что было в кэше,
                 * и заменить на новый актуальный список.
                 */
                usersDao.clearUsersCache()
                usersDao.insertUsersToCache(
                    usersList = tempList.map { userItemModel ->
                        userItemModel.mapToUserEntity()
                    }
                )

                /* Делаю здесь именно таким образом, чтобы соблюдался принцип
                 * Single Source of Truth.
                 * Т.е. вместо emit(NetworkResponseManager.Success(usersListFromApiAsModels))
                 * делаю таким образом, как ниже.
                 * Чтобы передавать пользоватлей только из базы данных/кэша и не было такого,
                 * что передаю и из Api, и из кэша.
                 */
                val allUsersFromCacheAsEntities = usersDao.getAllUsersFromCache()
                val allUsersFromCacheAsModels = allUsersFromCacheAsEntities.map { userEntity ->
                    userEntity.mapToUserItemModel()
                }

                emit(NetworkResponseManager.Success(data = allUsersFromCacheAsModels))
                emit(NetworkResponseManager.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getUserItemFromCacheById(userItemId: Int): Flow<UserItemModel> {
        return flow {
            val userItemEntity = usersDao.getUserById(userDataBaseId = userItemId)
            val userItemModel = userItemEntity.mapToUserItemModel()
            emit(userItemModel)
        }
    }

    override suspend fun insertUserItemToBookmarks(userItem: UserItemModel) {
        val userEntity = userItem.mapToUserEntity()
        usersDao.insertUserToBookmarks(user = userEntity)
    }

    /** Почему при удалении я делаю dao.InsertItem.
     * Потому, что т.к. я использую кэш - все пользователи всегда находятся в базе данных
     * и удалять мне ничего не нужно.
     * У пользователя просто есть параметр isInBookmarks, и если он false - значит пользователь
     * не в закладках, если true - значит в закладках.
     * Но пользователь ВСЕГДА находится в базе данных.
     * Поэтому при якобы удалении из закладки - я делаю снова insert, а не delete.
     * Просто перезаписываю параметр isInBookmarks на значение false.
     */
    override suspend fun deleteUserItemFromBookmarks(userItem: UserItemModel) {
        val userEntity = userItem.mapToUserEntity()
        usersDao.insertUserToBookmarks(user = userEntity)
    }

    override suspend fun getUsersInBookmarks(): Flow<List<UserItemModel>> {
        return flow {
            val usersListAsEntities = usersDao.getAllUsersInBookmarks(isInBookmarks = true)
            val usersListAsModels = usersListAsEntities.map { userItemEntity ->
                userItemEntity.mapToUserItemModel()
            }
            emit(usersListAsModels)
        }
    }
}