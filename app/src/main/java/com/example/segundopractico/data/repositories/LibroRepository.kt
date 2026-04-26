package com.example.segundopractico.data.repositories

import com.example.segundopractico.data.RetrofitInstance
import com.example.segundopractico.data.models.Genero
import com.example.segundopractico.data.models.Libro

class LibroRepository {
    private val api = RetrofitInstance.api
    suspend fun deleteGenero(id: Int): Boolean {
        return try {
            val response = RetrofitInstance.api.deleteGenero(id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
    suspend fun createGenero(genero: Genero): Boolean {
        return try {
            // Llama a la interfaz para enviar el JSON a la API [cite: 73]
            val response = RetrofitInstance.api.createGenero(genero)
            response.isSuccessful
        } catch (e: Exception) {
            false // Manejo de errores básico para el repositorio
        }
    }

    suspend fun getLibrosList(): List<Libro> = try { api.listLibros() } catch (e: Exception) { emptyList() }

    suspend fun getGenerosList(): List<Genero> = try { api.listGeneros() } catch (e: Exception) { emptyList() }

    suspend fun createLibro(libro: Libro): Boolean = try { api.createLibros(libro).isSuccessful } catch (e: Exception) { false }

    suspend fun updateLibro(id: Int, libro: Libro): Boolean = try { api.updateLibro(id, libro).isSuccessful } catch (e: Exception) { false }

    suspend fun deleteLibro(id: Int): Boolean = try { api.deleteLibro(id).isSuccessful } catch (e: Exception) { false }

}