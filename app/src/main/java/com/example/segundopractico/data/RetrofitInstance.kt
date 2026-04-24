package com.example.segundopractico.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitInstance {

    const val BASE_URL = "https://apilibreria.jmacboy.com/api/libros"

    val api: LibreriaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibreriaApiService::class.java)
    }
}
