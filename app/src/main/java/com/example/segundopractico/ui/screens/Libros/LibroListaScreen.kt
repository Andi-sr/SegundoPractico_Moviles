package com.example.segundopractico.ui.screens.Libros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.segundopractico.ui.viewmodels.LibroViewModel
import com.example.segundopractico.ui.viewmodels.LibroState
import coil.compose.AsyncImage // Asegúrate de tener la librería Coil instalada

@Composable
fun ListaDeLibros(
    viewModel: LibroViewModel,
    onAgregarClick: () -> Unit,
    onVerDetalle: (Int) -> Unit,
    onVerGeneros: () -> Unit
) {
    // 1. Escuchamos el estado del ViewModel (Loading, Success o Error)
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Lista de Libros",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Decidimos qué mostrar según el estado
            when (state) {
                is LibroState.Loading -> {
                    // Muestra el circulito de carga mientras espera a la API
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is LibroState.Error -> {
                    // Muestra el mensaje de error si falla el internet
                    Text(
                        text = (state as LibroState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is LibroState.Success -> {
                    val libros = (state as LibroState.Success).libros

                    LazyColumn {
                        // Aquí usamos la lista real que viene de la API
                        items(libros) { libro ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Row(modifier = Modifier.padding(12.dp)) {
                                    // Mostramos la imagen del libro (Punto 1 del PDF)
                                    AsyncImage(
                                        model = libro.imagen,
                                        contentDescription = null,
                                        modifier = Modifier.size(80.dp)
                                    )

                                    Column(modifier = Modifier.padding(start = 12.dp)) {
                                        Text(libro.nombre, style = MaterialTheme.typography.titleMedium)
                                        Text("Autor: ${libro.autor}")

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Button(
                                            onClick = { onVerDetalle(libro.id ?: 0) },
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text("Ver detalle")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Botón para ir a crear un libro nuevo
        FloatingActionButton(
            onClick = onAgregarClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("+", style = MaterialTheme.typography.headlineSmall)
        }
        Button(onClick = { onVerGeneros() }) {
            Text("Ver Géneros")
        }
    }
}