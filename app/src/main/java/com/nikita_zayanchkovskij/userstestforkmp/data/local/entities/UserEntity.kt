package com.nikita_zayanchkovskij.userstestforkmp.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikita_zayanchkovskij.userstestforkmp.common.constants.UsersAppConstants
import com.nikita_zayanchkovskij.userstestforkmp.data.remote.dto.AddressDto
import com.nikita_zayanchkovskij.userstestforkmp.data.remote.dto.CompanyDto

@Entity(tableName = UsersAppConstants.USER_ENTITY)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int? = null,
    val isInBookmarks: Boolean,
    val id: Int,
    val nameAndSurname: String,
    val username: String,
    val email: String,
    @Embedded
    val address: AddressDto,
    val phone: String,
    val website: String,
    @Embedded
    val company: CompanyDto
)
