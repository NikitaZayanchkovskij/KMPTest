package com.nikita_zayanchkovskij.userstestforkmp.domain.models

data class UserItemModel(
    val roomId: Int? = null,
    val isInBookmarks: Boolean,
    val userId: Int,
    val nameAndSurname: String,
    val userNickName: String,
    val email: String,
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val phone: String,
    val website: String,
    val companyName: String,
    val catchPhrase: String,
    val bs: String
)
