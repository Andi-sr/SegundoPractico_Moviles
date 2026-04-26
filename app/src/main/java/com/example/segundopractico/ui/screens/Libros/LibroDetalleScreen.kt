package com.example.segundopractico.ui.screens.Libros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.segundopractico.ui.viewmodels.LibroViewModel
import com.example.segundopractico.ui.viewmodels.LibroState
import coil.compose.AsyncImage
import com.example.segundopractico.data.models.Libro

@Composable
fun DetallesLibros(
    libroId: Int,
    viewModel: LibroViewModel,
    onNavigateToEdit: (Int) -> Unit,
    onBack: () -> Unit
) {
    // Buscamos el libro real dentro del estado del ViewModel
    val state by viewModel.uiState.collectAsState()
    val libro = (state as? LibroState.Success)?.libros?.find { it.id == libroId }

    // Estado para mostrar el diálogo de confirmación de borrado
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (libro == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("No se encontró la información del libro", modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Por si la sinopsis es larga
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Imagen del libro (Requisito del PDF)
                AsyncImage(
                    model = libro.imagen,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Nombre: ${libro.nombre}", style = MaterialTheme.typography.titleLarge)
                Text("Autor: ${libro.autor}")
                Text("Editorial: ${libro.editorial}")
                Text("ISBN: ${libro.isbn}")
                Text("Calificación: ${libro.calificacion}/5")

                Spacer(modifier = Modifier.height(12.dp))

                Text("Sinopsis:", style = MaterialTheme.typography.titleMedium)
                Text(libro.sinopsis)

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    // Botón Editar
                    Button(
                        onClick = { onNavigateToEdit(libroId) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Editar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón Eliminar con color de alerta
                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }

    // DIÁLOGO DE CONFIRMACIÓN (Obligatorio para una buena nota)
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar este libro? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.eliminarLibro(libroId)
                    showDeleteDialog = false
                    onBack() // Volvemos a la lista después de borrar
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}