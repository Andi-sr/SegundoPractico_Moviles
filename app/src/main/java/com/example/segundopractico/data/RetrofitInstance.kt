package com.example.segundopractico.data
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://apilibreria.jmacboy.com/api/"

    val api: LibreriaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibreriaApiService::class.java)
    }
}
