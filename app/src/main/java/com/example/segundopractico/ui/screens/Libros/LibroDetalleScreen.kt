package com.example.segundopractico.ui.screens.Libros

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetallesLibros() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                "Detalle del Libro",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Nombre: Libro ejemplo")
            Text("Autor: Autor ejemplo")
            Text("Editorial: Editorial X")
            Text("ISBN: 123456")
            Text("Calificación: 4.5")

            Spacer(modifier = Modifier.height(12.dp))

            Text("Sinopsis:")
            Text("Aquí va la descripción del libro...")

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()) {
                Button(onClick = {}) {
                    Text("Editar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {}) {
                    Text("Eliminar")
                }
            }
        }
    }
}
