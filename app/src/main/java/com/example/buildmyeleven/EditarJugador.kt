package com.example.buildmyeleven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.buildmyeleven.data.JugadorRepository
import com.example.buildmyeleven.data.db.AppDatabase
import com.example.buildmyeleven.data.model.Jugador
import com.example.buildmyeleven.viewmodel.JugadorViewModel
import com.example.buildmyeleven.viewmodel.JugadorViewModelFactory

class EditarJugador : Fragment() {

    private lateinit var viewModel: JugadorViewModel
    private var jugadorId: Int = -1
    private lateinit var jugador: Jugador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_editar_jugador, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = AppDatabase.getDatabase(requireContext()).jugadorDao()
        val repo = JugadorRepository(dao)
        val factory = JugadorViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory).get(JugadorViewModel::class.java)

        val spinnerPieHabil = view.findViewById<Spinner>(R.id.spinnerPieHabil)

    // Opciones del Spinner (ejemplo)
        val opcionesPie = listOf("Izquierdo", "Derecho", "Ambidiestro")

    // Crea el adapter y asigna al spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesPie)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPieHabil.adapter = adapter

        jugadorId = arguments?.getInt("jugadorId") ?: -1
        if (jugadorId == -1) {
            Toast.makeText(requireContext(), "Error: jugadorId no recibido", Toast.LENGTH_SHORT).show()
            // Evitar seguir porque no hay jugadorId válido
            return
        } else {
            println("DEBUG: jugadorId recibido = $jugadorId")
        }


        viewModel.obtenerJugadorPorId(jugadorId).observe(viewLifecycleOwner) { jugadorEncontrado ->
            if (jugadorEncontrado != null) {
                jugador = jugadorEncontrado

                // Llena los EditTexts con la info del jugador
                view.findViewById<EditText>(R.id.etNombre).setText(jugador.nombre)
                view.findViewById<EditText>(R.id.etApellido).setText(jugador.apellido)
                view.findViewById<EditText>(R.id.etFechaNacimiento).setText(jugador.fechaNacimiento)
                view.findViewById<EditText>(R.id.etPosicion).setText(jugador.posicion)

                val posicionPie = opcionesPie.indexOf(jugador.pieHabil)
                if (posicionPie >= 0) {
                    spinnerPieHabil.setSelection(posicionPie)
                } else {
                    spinnerPieHabil.setSelection(0) // opción por defecto si no encuentra
                }

                view.findViewById<EditText>(R.id.etEquipoAnterior).setText(jugador.equipoAnterior)
            }else {
                Toast.makeText(requireContext(), "Jugador no encontrado", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.btnEditarJugador).setOnClickListener {

            if (!::jugador.isInitialized) {
                Toast.makeText(requireContext(), "Jugador no cargado aún", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val jugadorActualizado = jugador.copy(
                nombre = view.findViewById<EditText>(R.id.etNombre).text.toString(),
                apellido = view.findViewById<EditText>(R.id.etApellido).text.toString(),
                fechaNacimiento = view.findViewById<EditText>(R.id.etFechaNacimiento).text.toString(),
                posicion = view.findViewById<EditText>(R.id.etPosicion).text.toString(),
                pieHabil = spinnerPieHabil.selectedItem.toString(),
                equipoAnterior = view.findViewById<EditText>(R.id.etEquipoAnterior).text.toString()
            )

            viewModel.editarJugador(jugadorActualizado)

            Toast.makeText(requireContext(), "Jugador actualizado", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_editarJugador_to_jugadoresList)
        }
    }
}
