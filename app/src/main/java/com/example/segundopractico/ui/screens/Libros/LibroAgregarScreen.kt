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
fun AgregarLibro() {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        Text("Agregar Libro", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Nombre") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Autor") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Editorial") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("ISBN") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Imagen URL") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Sinopsis") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
