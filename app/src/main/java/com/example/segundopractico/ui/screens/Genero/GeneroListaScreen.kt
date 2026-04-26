package com.example.segundopractico.ui.screens.Genero

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.segundopractico.data.models.Genero
import com.example.segundopractico.ui.viewmodels.LibroState
import com.example.segundopractico.ui.viewmodels.LibroViewModel

@Composable
fun ListaGeneros(viewModel: LibroViewModel) {
    val state by viewModel.uiState.collectAsState()
    var nuevoNombre by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Estado para manejar el diálogo de confirmación
    var generoAEliminar by remember { mutableStateOf<Genero?>(null) }

    // --- Lógica del Diálogo de Confirmación ---
    generoAEliminar?.let { genero ->
        AlertDialog(
            onDismissRequest = { generoAEliminar = null },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás segura de que deseas eliminar el género '${genero.nombre}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        genero.id?.let { id ->
                            viewModel.eliminarGenero(id) // Eliminación vía API [cite: 61]
                        }
                        generoAEliminar = null
                        Toast.makeText(context, "Eliminando género...", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { generoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Gestión de Géneros", style = MaterialTheme.typography.headlineMedium)

        Box(modifier = Modifier.weight(1f)) {
            when (state) {
                is LibroState.Loading -> {
                    // Indicador de carga [cite: 51, 67]
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is LibroState.Success -> {
                    // Mostrar lista obtenida de la API [cite: 49, 50, 68]
                    val generos = (state as LibroState.Success).generos
                    LazyColumn {
                        items(generos) { genero ->
                            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(genero.nombre, modifier = Modifier.weight(1f))
                                    IconButton(onClick = {
                                        // Activamos el diálogo en lugar de borrar directo
                                        generoAEliminar = genero
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is LibroState.Error -> {
                    // Manejo de errores de red [cite: 51, 63, 69]
                    Text("Error al cargar datos", color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario para crear género [cite: 52, 53]
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = nuevoNombre,
                onValueChange = { nuevoNombre = it },
                label = { Text("Nuevo Género") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (nuevoNombre.isBlank()) {
                        // Validación de nombre obligatorio
                        Toast.makeText(context, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.insertarGenero(nuevoNombre) // Envío a API [cite: 55]
                        nuevoNombre = ""
                    }
                }
            ) {
                Text("Guardar")
            }
        }
    }
}