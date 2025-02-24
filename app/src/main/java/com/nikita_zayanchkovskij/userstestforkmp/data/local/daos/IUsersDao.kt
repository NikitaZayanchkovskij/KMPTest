package com.nikita_zayanchkovskij.userstestforkmp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikita_zayanchkovskij.userstestforkmp.common.constants.UsersAppConstants
import com.nikita_zayanchkovskij.userstestforkmp.data.local.entities.UserEntity

@Dao
interface IUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersToCache(usersList: List<UserEntity>)

    @Query("SELECT * FROM ${UsersAppConstants.USER_ENTITY}")
    suspend fun getAllUsersFromCache(): List<UserEntity>

    @Query("DELETE FROM ${UsersAppConstants.USER_ENTITY}")
    suspend fun clearUsersCache()

    @Query("SELECT * FROM ${UsersAppConstants.USER_ENTITY} WHERE isInBookmarks LIKE :isInBookmarks")
    suspend fun getAllUsersInBookmarks(isInBookmarks: Boolean): List<UserEntity>

    @Query("SELECT * FROM ${UsersAppConstants.USER_ENTITY} WHERE roomId LIKE :userDataBaseId")
    suspend fun getUserById(userDataBaseId: Int): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserToBookmarks(user: UserEntity)
}