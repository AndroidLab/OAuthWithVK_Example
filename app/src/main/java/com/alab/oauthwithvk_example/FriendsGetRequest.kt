package com.alab.oauthwithvk_example

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Определяет запрос друзей пользователя.
 */
interface FriendsGetRequest {
    /**
     * Возвращает json со списком друзей.
     */
    @GET("friends.get")
    suspend fun friendsGet(
        @Query("access_token") token: String,
        @Query("v") v: String,
        @Query("fields") fields: String
    ): String
}