package com.example.segundopractico.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.segundopractico.data.models.Libro
import com.example.segundopractico.data.repositories.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class LibroViewModel : ViewModel() {

    private val repository = LibroRepository()

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: MutableStateFlow<List<Libro>> = _libros

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun cargarLibros() {
        viewModelScope.launch {
            _loading.value = true
            _libros.value = repository.getLibrosList()
            _loading.value = false
        }
    }

}

@Composable
fun LibrosScreen(viewModel: LibroViewModel) {

    val libros by viewModel.libros.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarLibros()
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Lista de Libros")

        if (loading) {
            CircularProgressIndicator()
        }

        if (libros.isEmpty() && !loading) {
            Text("No hay datos")
        }

        LazyColumn {
            items(libros) { Libro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = "Libro ${Libro.nombre}")
                        Text(text = "Autor: ${Libro.autor}")
                        Text(text = "ISBN: ${Libro.isbn}")
                    }
                }
            }
        }
    }
}


