package com.example.buildmyeleven.data

import androidx.lifecycle.LiveData
import com.example.buildmyeleven.data.dao.JugadorDao
import com.example.buildmyeleven.data.model.Jugador

class JugadorRepository(private val jugadorDao: JugadorDao) {

    val todosLosJugadores: LiveData<List<Jugador>> = jugadorDao.listarJugadores()

    suspend fun insertar(jugador: Jugador) {
        jugadorDao.insertJugador(jugador)
    }

    fun obtenerJugadorPorId(id: Int): LiveData<Jugador> {
        return jugadorDao.obtenerJugadorPorId(id)
    }

    suspend fun eliminarjugadorporId(jugadorId: Int) {
        jugadorDao.eliminarJugadorPorId(jugadorId)
    }

}
