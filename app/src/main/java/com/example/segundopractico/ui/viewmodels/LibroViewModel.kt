package com.example.segundopractico.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.segundopractico.data.models.Genero
import com.example.segundopractico.data.models.Libro
import com.example.segundopractico.data.repositories.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Requisito: Estados obligatorios [cite: 65, 67, 68, 69]
sealed class LibroState {
    object Loading : LibroState()
    data class Success(val libros: List<Libro>, val generos: List<Genero>) : LibroState()
    data class Error(val message: String) : LibroState()
}

class LibroViewModel : ViewModel() {
    private val repository = LibroRepository()

    // Requisito: Uso obligatorio de State Flow [cite: 70]
    private val _uiState = MutableStateFlow<LibroState>(LibroState.Loading)
    val uiState: StateFlow<LibroState> = _uiState

    init {
        fetchLibros()
    }

    // Requisito: Obtener lista desde la API [cite: 5, 49]
    fun fetchLibros() {
        viewModelScope.launch {
            _uiState.value = LibroState.Loading
            try {
                val libros = repository.getLibrosList()
                val generos = repository.getGenerosList()
                _uiState.value = LibroState.Success(libros, generos)
            } catch (e: Exception) {
                // Requisito: Manejo de errores de red [cite: 9, 45, 63]
                _uiState.value = LibroState.Error("Error de red: ${e.message}")
            }
        }
    }

    // Requisito: Crear género con refresco automático [cite: 52, 55, 56]
    fun insertarGenero(nombre: String) {
        viewModelScope.launch {
            try {
                val nuevo = Genero(id = null, nombre = nombre)
                if (repository.createGenero(nuevo)) {
                    fetchLibros() // Refresco automático exigido [cite: 56]
                }
            } catch (e: Exception) {
                _uiState.value = LibroState.Error("Error al crear género")
            }
        }
    }

    // Requisito: Eliminar género con refresco automático [cite: 57, 61, 62]
    fun eliminarGenero(id: Int) {
        viewModelScope.launch {
            try {
                // Eliminación vía API
                val exito = repository.deleteGenero(id)
                if (exito) {
                    fetchLibros() // Refresco automático exigido [cite: 62]
                }
            } catch (e: Exception) {
                // Manejo de errores [cite: 63]
                _uiState.value = LibroState.Error("No se pudo eliminar el género")
            }
        }
    }

    // Funciones adicionales para Libros (Puntos 3, 4 y 5 del PDF) [cite: 23, 34, 40]
    fun insertarLibro(libro: Libro) {
        viewModelScope.launch {
            if (repository.createLibro(libro)) fetchLibros()
        }
    }

    fun actualizarLibro(id: Int, libro: Libro) {
        viewModelScope.launch {
            if (repository.updateLibro(id, libro)) fetchLibros()
        }
    }

    fun eliminarLibro(id: Int) {
        viewModelScope.launch {
            if (repository.deleteLibro(id)) fetchLibros()
        }
    }
}