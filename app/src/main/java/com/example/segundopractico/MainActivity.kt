package com.example.segundopractico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.segundopractico.ui.screens.Genero.AñadirGenero
import com.example.segundopractico.ui.screens.Genero.ListaGeneros
import com.example.segundopractico.ui.screens.Libros.EditarLibro
import com.example.segundopractico.ui.screens.Libros.ListaDeLibros


class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContent {
                ListaGeneros()
            }
        }
    }
