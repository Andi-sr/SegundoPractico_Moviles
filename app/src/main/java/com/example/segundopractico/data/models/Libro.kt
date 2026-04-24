package com.example.segundopractico.data.models

    data class Libro(
        val nombre: String,
        val autor: String,
        val editorial: String,
        val imagen: String,
        val sinopsis: String,
        val isbn: String,
        val calificacion: Double,
        val generos: List<Genero>
    )
