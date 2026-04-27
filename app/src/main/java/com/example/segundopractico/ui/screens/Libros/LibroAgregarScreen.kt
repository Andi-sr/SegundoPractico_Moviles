package com.example.segundopractico.ui.screens.Libros

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.segundopractico.data.models.Libro
import com.example.segundopractico.data.models.Genero
import com.example.segundopractico.ui.viewmodels.LibroState
import com.example.segundopractico.ui.viewmodels.LibroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroAgregarScreen(
    viewModel: LibroViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val state by viewModel.uiState.collectAsState()


    var nombre by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var editorial by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var calificacion by remember { mutableStateOf("") }


    var generosSeleccionados by remember { mutableStateOf(setOf<Int>()) }

    var isSubmitting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {

        Text("Agregar Nuevo Libro", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(autor, { autor = it }, label = { Text("Autor") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(editorial, { editorial = it }, label = { Text("Editorial") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(isbn, { isbn = it }, label = { Text("ISBN") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(imagenUrl, { imagenUrl = it }, label = { Text("URL Imagen") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(calificacion, { calificacion = it }, label = { Text("Calificación (1-5)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            sinopsis,
            { sinopsis = it },
            label = { Text("Sinopsis") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔥 GÉNEROS
        Text("Seleccionar Géneros", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        when (state) {
            is LibroState.Loading -> {
                CircularProgressIndicator()
            }

            is LibroState.Success -> {
                val generos = (state as LibroState.Success).generos

                if (generos.isEmpty()) {
                    Text("No hay géneros disponibles")
                } else {
                    generos.forEach { genero ->

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Checkbox(
                                checked = generosSeleccionados.contains(genero.id),
                                onCheckedChange = { isChecked ->
                                    generosSeleccionados = if (isChecked) {
                                        generosSeleccionados + (genero.id ?: 0)
                                    } else {
                                        generosSeleccionados - (genero.id ?: 0)
                                    }
                                }
                            )

                            Text(genero.nombre)
                        }
                    }
                }
            }

            is LibroState.Error -> {
                Text("Error al cargar géneros", color = Color.Red)
            }

            else -> {}
        }

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = {

                val error = validarFormulario(nombre, isbn, imagenUrl, calificacion)

                if (error != null) {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                } else {

                    isSubmitting = true

                    val nuevoLibro = Libro(
                        id = null,
                        nombre = nombre,
                        autor = autor,
                        editorial = editorial,
                        imagen = imagenUrl,
                        sinopsis = sinopsis,
                        isbn = isbn,
                        calificacion = calificacion.toDoubleOrNull() ?: 0.0,

                        // 🔥 GÉNEROS SELECCIONADOS
                        generos = generosSeleccionados.map { id ->
                            Genero(id = id, nombre = "")
                        }
                    )

                    viewModel.insertarLibro(nuevoLibro)

                    Toast.makeText(context, "Libro guardado", Toast.LENGTH_SHORT).show()

                    onBack()
                }
            },
            enabled = !isSubmitting,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Guardar Libro")
            }
        }
    }
}

// Función de validación obligatoria
fun validarFormulario(nombre: String, isbn: String, url: String, calif: String): String? {
    if (nombre.isBlank() || isbn.isBlank() || url.isBlank()) return "Nombre, ISBN e Imagen son obligatorios"

    // Validación de formato ISBN (Punto 3 del PDF)
    val isbnRegex = Regex("^[0-9-]{10,17}$")
    if (!isbn.matches(isbnRegex)) return "Formato de ISBN inválido"

    val c = calif.toDoubleOrNull()
    if (c == null || c < 1 || c > 5) return "La calificación debe ser entre 1 y 5"

    if (!android.util.Patterns.WEB_URL.matcher(url).matches()) {
        return "URL inválida"
    }

    return null
}