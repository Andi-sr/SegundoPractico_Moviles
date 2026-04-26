package com.example.segundopractico.ui.screens.Libros

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.segundopractico.data.models.Libro
import com.example.segundopractico.data.models.Genero
import com.example.segundopractico.ui.viewmodels.LibroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroAgregarScreen(
    viewModel: LibroViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Variables para el formulario
    var nombre by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var editorial by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var calificacion by remember { mutableStateOf("") }

    // Para evitar envíos duplicados (Punto 8 del PDF)
    var isSubmitting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Agregar Nuevo Libro", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del Libro") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = editorial, onValueChange = { editorial = it }, label = { Text("Editorial") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = isbn, onValueChange = { isbn = it }, label = { Text("ISBN (Ej: 123-456-789)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = imagenUrl, onValueChange = { imagenUrl = it }, label = { Text("URL de la Imagen") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = calificacion, onValueChange = { calificacion = it }, label = { Text("Calificación (1-5)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = sinopsis, onValueChange = { sinopsis = it }, label = { Text("Sinopsis") }, modifier = Modifier.fillMaxWidth(), minLines = 3)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // 1. Validamos primero
                val error = validarFormulario(nombre, isbn, imagenUrl, calificacion)
                if (error != null) {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                } else {
                    isSubmitting = true

                    // 2. Creamos el objeto con los datos de los TextField
                    val nuevoLibro = Libro(
                        id = null, // IMPORTANTE: Enviamos null para que la API genere el ID
                        nombre = nombre,
                        autor = autor,
                        editorial = editorial,
                        imagen = imagenUrl,
                        sinopsis = sinopsis,
                        isbn = isbn,
                        calificacion = calificacion.toDoubleOrNull() ?: 0.0,
                        generos = emptyList()
                    )

                    // 3. LLAMADA REAL AL VIEWMODEL (Usando la función que agregamos antes)
                    viewModel.insertarLibro(nuevoLibro)

                    Toast.makeText(context, "¡Libro guardado con éxito!", Toast.LENGTH_SHORT).show()

                    // 4. Volvemos atrás a la lista
                    onBack()
                }
            },
            enabled = !isSubmitting,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isSubmitting) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
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

    return null
}