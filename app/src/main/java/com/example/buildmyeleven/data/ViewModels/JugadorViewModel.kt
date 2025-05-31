package com.example.buildmyeleven.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildmyeleven.data.JugadorRepository
import com.example.buildmyeleven.data.model.Jugador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JugadorViewModel(private val repository: JugadorRepository) : ViewModel() {

    fun insertar(jugador: Jugador) = viewModelScope.launch {
        repository.insertar(jugador)
    }

    fun obtenerJugadorPorId(id: Int): LiveData<Jugador> {
        return repository.obtenerJugadorPorId(id)
    }

    fun editarJugador(jugador: Jugador) {
        viewModelScope.launch {
            repository.editarJugador(jugador)
        }
    }

    fun deleteJugador(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarjugadorporId(id)
        }
    }

    val todosLosJugadores = repository.todosLosJugadores
}

