package com.example.buildmyeleven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildmyeleven.data.JugadorRepository
import com.example.buildmyeleven.data.dao.JugadorDao
import com.example.buildmyeleven.data.db.AppDatabase
import com.example.buildmyeleven.viewmodel.JugadorViewModel
import com.example.buildmyeleven.viewmodel.JugadorViewModelFactory

class JugadoresList : Fragment() {

    private lateinit var rvJugadores: RecyclerView
    private lateinit var btnNuevoJugador: Button
    private lateinit var btnListaJugadores: Button
    private lateinit var adapter: JugadorAdapter

    // Crear ViewModel con factory para pasar repositorio
    private val jugadorViewModel: JugadorViewModel by viewModels {
        val dao: JugadorDao = AppDatabase.getDatabase(requireContext()).jugadorDao()
        val repo = JugadorRepository(dao)
        JugadorViewModelFactory(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jugadores_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvJugadores = view.findViewById(R.id.rvJugadores)
        btnNuevoJugador = view.findViewById(R.id.btnNuevoJugador)
        btnListaJugadores = view.findViewById(R.id.btnListaJugadores)

        adapter = JugadorAdapter { jugadorId ->
            val action = JugadoresListDirections.actionJugadoresListToDetalleJugador(jugadorId as Int)
            findNavController().navigate(action)
        }

        rvJugadores.layoutManager = LinearLayoutManager(requireContext())
        rvJugadores.adapter = adapter

        // Observar lista de jugadores desde ViewModel y actualizar adapter
        jugadorViewModel.todosLosJugadores.observe(viewLifecycleOwner, Observer { jugadores ->
            adapter.setJugadores(jugadores)
        })

        btnNuevoJugador.setOnClickListener {
            findNavController().navigate(R.id.action_jugadoresList_to_nuevoJugador)
        }

        btnListaJugadores.setOnClickListener {
            Toast.makeText(requireContext(), "Ya estás en la lista de jugadores", Toast.LENGTH_SHORT).show()
        }
    }
}
