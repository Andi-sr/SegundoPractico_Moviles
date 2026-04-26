package com.example.segundopractico.data

import com.example.segundopractico.data.models.Genero
import com.example.segundopractico.data.models.Libro
import retrofit2.Response
import retrofit2.http.*

interface LibreriaApiService {
        @GET("libros")
        suspend fun listLibros(): List<Libro>

        @POST("libros")
        suspend fun createLibros(@Body libro: Libro): Response<Libro>

        @PUT("libros/{id}")
        suspend fun updateLibro(@Path("id") id: Int, @Body libro: Libro): Response<Libro>

        @DELETE("libros/{id}")
        suspend fun deleteLibro(@Path("id") id: Int): Response<Unit>

        @GET("generos")
        suspend fun listGeneros(): List<Genero>

        @POST("generos")
        suspend fun createGenero(@Body genero: Genero): Response<Genero>

        @DELETE("generos/{id}")
        suspend fun deleteGenero(@Path("id") id: Int): retrofit2.Response<Unit>
}