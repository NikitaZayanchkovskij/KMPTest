package com.nikita_zayanchkovskij.userstestforkmp.data.mappers

import com.nikita_zayanchkovskij.userstestforkmp.data.local.entities.UserEntity
import com.nikita_zayanchkovskij.userstestforkmp.data.remote.dto.AddressDto
import com.nikita_zayanchkovskij.userstestforkmp.data.remote.dto.CompanyDto
import com.nikita_zayanchkovskij.userstestforkmp.data.remote.dto.UserDto
import com.nikita_zayanchkovskij.userstestforkmp.domain.models.UserItemModel

fun UserEntity.mapToUserItemModel(): UserItemModel {
    val userItemModel = UserItemModel(
        roomId = roomId,
        isInBookmarks = isInBookmarks,
        userId = id,
        nameAndSurname = nameAndSurname,
        userNickName = username,
        email = email,
        street = address.street,
        suite = address.suite,
        city = address.city,
        zipcode = address.zipcode,
        phone = phone,
        website = website,
        companyName = company.name,
        catchPhrase = company.catchPhrase,
        bs = company.bs
    )
    return userItemModel
}

fun UserItemModel.mapToUserEntity(): UserEntity {
    val userEntity = UserEntity(
        roomId = roomId,
        isInBookmarks = isInBookmarks,
        id = userId,
        nameAndSurname = nameAndSurname,
        username = userNickName,
        email = email,
        address = AddressDto(
            street = street,
            suite = suite,
            city = city,
            zipcode = zipcode
        ),
        phone = phone,
        website = website,
        company = CompanyDto(
            name = companyName,
            catchPhrase = catchPhrase,
            bs = bs
        )
    )
    return userEntity
}

fun UserDto.mapToUserItemModel(): UserItemModel {
    val userItemModel = UserItemModel(
        roomId = null,
        isInBookmarks = false,
        userId = id,
        nameAndSurname = name,
        userNickName = username,
        email = email,
        street = address.street,
        suite = address.suite,
        city = address.city,
        zipcode = address.zipcode,
        phone = phone,
        website = website,
        companyName = company.name,
        catchPhrase = company.catchPhrase,
        bs = company.bs
    )
    return userItemModel
}