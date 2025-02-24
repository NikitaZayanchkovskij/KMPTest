package com.nikita_zayanchkovskij.userstestforkmp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikita_zayanchkovskij.userstestforkmp.data.local.daos.IUsersDao
import com.nikita_zayanchkovskij.userstestforkmp.data.local.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UsersDataBase: RoomDatabase() {
    abstract val usersDao: IUsersDao
}