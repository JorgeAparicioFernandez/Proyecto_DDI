package com.example.buildmyeleven.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "jugadores")
data class Jugador(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: String,
    val posicion: String,
    val pieHabil: String,
    val equipoAnterior: String,
    val categoria: String
) : Parcelable
