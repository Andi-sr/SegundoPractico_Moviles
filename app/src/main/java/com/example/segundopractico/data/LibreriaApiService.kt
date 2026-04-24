package com.example.segundopractico.data

import com.example.segundopractico.data.models.Libro
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LibreriaApiService {

        @GET("libros")
        suspend fun listLibros(): List<Libro>

        @POST("libros")
        suspend fun createLibros(@Body libro: Libro): Libro

}