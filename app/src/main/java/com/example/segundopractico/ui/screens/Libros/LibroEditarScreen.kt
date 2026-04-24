package com.example.segundopractico.ui.screens.Libros

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun EditarLibro() {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        Text("Editar Libro", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = "Libro ejemplo", onValueChange = {}, label = { Text("Nombre") })
        OutlinedTextField(value = "Autor ejemplo", onValueChange = {}, label = { Text("Autor") })
        OutlinedTextField(value = "Editorial X", onValueChange = {}, label = { Text("Editorial") })
        OutlinedTextField(value = "123456", onValueChange = {}, label = { Text("ISBN") })
        OutlinedTextField(value = "https://imagen.com", onValueChange = {}, label = { Text("Imagen URL") })
        OutlinedTextField(value = "Descripción...", onValueChange = {}, label = { Text("Sinopsis") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar")
        }
    }
}
