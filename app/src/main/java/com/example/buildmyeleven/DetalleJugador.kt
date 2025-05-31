package com.example.buildmyeleven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.buildmyeleven.data.JugadorRepository
import com.example.buildmyeleven.data.db.AppDatabase
import com.example.buildmyeleven.viewmodel.JugadorViewModel
import com.example.buildmyeleven.viewmodel.JugadorViewModelFactory

class DetalleJugador : Fragment() {

    private var jugadorId: Int = -1
    private lateinit var btnEliminarJugador: Button
    private lateinit var btnEditarJugador: Button

    private val jugadorViewModel: JugadorViewModel by viewModels {
        val dao = AppDatabase.getDatabase(requireContext()).jugadorDao()
        val repo = JugadorRepository(dao)
        JugadorViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jugadorId = DetalleJugadorArgs.fromBundle(requireArguments()).jugadorId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalle_jugador, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnEliminarJugador = view.findViewById(R.id.btnEliminarJugadores)
        btnEditarJugador = view.findViewById(R.id.btnEditarJugador)

        val etNombre = view.findViewById<EditText>(R.id.etNombre)
        val etApellido = view.findViewById<EditText>(R.id.etApellido)
        val etFechaNacimiento = view.findViewById<EditText>(R.id.etFechaNacimiento)
        val etPosicion = view.findViewById<EditText>(R.id.etPosicion)
        val etPieHabil = view.findViewById<EditText>(R.id.etPieHabil)
        val etEquipoAnterior = view.findViewById<EditText>(R.id.etEquipoAnterior)

        val jugadorId = DetalleJugadorArgs.fromBundle(requireArguments()).jugadorId

        jugadorViewModel.obtenerJugadorPorId(jugadorId).observe(viewLifecycleOwner) { jugador ->
            if (jugador != null) {
                etNombre.setText(jugador.nombre)
                etApellido.setText(jugador.apellido)
                etFechaNacimiento.setText(jugador.fechaNacimiento)
                etPosicion.setText(jugador.posicion)
                etPieHabil.setText(jugador.pieHabil)
                etEquipoAnterior.setText(jugador.equipoAnterior)

                btnEditarJugador.setOnClickListener {

                    val action = DetalleJugadorDirections.actionDetalleJugadorToEditarJugador(jugador.id)
                    findNavController().navigate(action)

                }

            btnEliminarJugador.setOnClickListener {
                val jugadorId = jugador.id
                jugadorViewModel.deleteJugador(jugadorId)
                Toast.makeText(requireContext(), "Jugador eliminado", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_DetalleJugador_to_jugadoresList)

            }

        }

    }
}
}
