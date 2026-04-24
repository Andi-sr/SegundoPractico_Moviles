package com.example.segundopractico.data.repositories

import com.example.segundopractico.data.RetrofitInstance
import com.example.segundopractico.data.models.Libro

class LibroRepository {
    suspend fun getLibrosList(): List<Libro> {
        try {
            val retrofitInstance = RetrofitInstance.api
            return retrofitInstance.listLibros()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }
    suspend fun createLibro(libro: Libro): Libro? {
        try {
            val retrofitInstance = RetrofitInstance.api
            return retrofitInstance.createLibros(libro)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}