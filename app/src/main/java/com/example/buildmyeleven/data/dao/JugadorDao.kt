package com.example.buildmyeleven.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.buildmyeleven.data.model.Jugador

@Dao
interface JugadorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJugador(jugador: Jugador)

    @Query("SELECT * FROM jugadores ORDER BY nombre ASC")
    fun listarJugadores(): LiveData<List<Jugador>>

    @Query("SELECT * FROM jugadores WHERE id = :id")
    fun obtenerJugadorPorId(id: Int): LiveData<Jugador>

    @Query("DELETE FROM jugadores WHERE id = :jugadorId")
    suspend fun eliminarJugadorPorId(jugadorId: Int)
}

