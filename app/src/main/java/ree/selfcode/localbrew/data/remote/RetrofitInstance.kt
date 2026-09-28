package ree.selfcode.localbrew.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: GeoapifyApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.geoapify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeoapifyApiService::class.java)
    }
}
