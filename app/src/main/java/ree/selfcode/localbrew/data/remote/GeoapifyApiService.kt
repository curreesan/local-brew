package ree.selfcode.localbrew.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyApiService {
    @GET("v2/places")
    suspend fun getNearbyCafes(
        @Query("categories") categories: String,
        @Query("filter") filter: String,
        @Query("apiKey") apiKey: String,
        @Query("name") name: String?
    ): GeoapifyResponse
}
