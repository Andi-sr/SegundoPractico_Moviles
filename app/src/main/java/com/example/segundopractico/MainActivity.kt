package com.example.segundopractico // Revisa que este sea tu paquete real

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
// Importamos todas las pantallas de tus carpetas
import com.example.segundopractico.ui.screens.Libros.*
import com.example.segundopractico.ui.screens.Genero.ListaGeneros
import com.example.segundopractico.ui.viewmodels.LibroViewModel
import com.example.segundopractico.ui.theme.SegundoPracticoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SegundoPracticoTheme {

                // 1. Inicializamos el navegador y el ViewModel
                val navController = rememberNavController()
                val libroViewModel: LibroViewModel = viewModel()

                // 2. Definimos las rutas de la aplicación
                NavHost(
                    navController = navController,
                    startDestination = "lista"
                ) {

                    // Pantalla: Lista de Libros (Inicio)
                    composable("lista") {
                        // Revisa si tu función en LibroListaScreen se llama ListaDeLibros
                        ListaDeLibros(
                            viewModel = libroViewModel,
                            onAgregarClick = { navController.navigate("agregar") },
                            onVerDetalle = { id -> navController.navigate("detalle/$id") },
                            onVerGeneros = { navController.navigate("generos") }
                        )
                    }

                    // Pantalla: Gestión de Géneros
                    composable("generos") {
                        ListaGeneros(viewModel = libroViewModel)
                    }

                    // Pantalla: Formulario para Agregar Libro
                    composable("agregar") {
                        LibroAgregarScreen(
                            viewModel = libroViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    // Pantalla: Detalle del Libro (Recibe ID)
                    composable(
                        route = "detalle/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id") ?: 0
                        DetallesLibros(
                            libroId = id,
                            viewModel = libroViewModel,
                            onNavigateToEdit = { libroId ->
                                // Opcional: navController.navigate("editar/$libroId")
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}