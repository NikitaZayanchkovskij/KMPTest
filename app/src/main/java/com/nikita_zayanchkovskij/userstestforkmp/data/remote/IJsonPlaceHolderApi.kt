package com.nikita_zayanchkovskij.userstestforkmp.data.remote

import com.nikita_zayanchkovskij.userstestforkmp.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface IJsonPlaceHolderApi {

    @GET("users")
    suspend fun getUsers(): Response<List<UserDto>>
}