package com.example.buildmyeleven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.buildmyeleven.data.JugadorRepository
import com.example.buildmyeleven.data.db.AppDatabase
import com.example.buildmyeleven.data.model.Jugador
import com.example.buildmyeleven.databinding.FragmentNuevoJugadorBinding
import com.example.buildmyeleven.viewmodel.JugadorViewModel
import com.example.buildmyeleven.viewmodel.JugadorViewModelFactory

class NuevoJugador : Fragment() {

    private var _binding: FragmentNuevoJugadorBinding? = null
    private lateinit var jugadorViewModel: JugadorViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevoJugadorBinding.inflate(inflater, container, false)
        val view = binding.root

        val spinner = binding.spinnerPieHabil

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getDatabase(application).jugadorDao()
        val repository = JugadorRepository(dao)
        val factory = JugadorViewModelFactory(repository)
        jugadorViewModel = ViewModelProvider(this, factory)[JugadorViewModel::class.java]


        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opciones_pie_habil,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.btnListaJugadores.setOnClickListener {
            findNavController().navigate(R.id.action_nuevoJugador_to_jugadoresList)
        }

        binding.btnNuevoJugador.setOnClickListener {
            Toast.makeText(requireContext(), "Ya estás en la pagina de nuevo jugador", Toast.LENGTH_SHORT).show()
        }

        binding.btnGuardarJugador.setOnClickListener {
            guardarJugador()
        }

        return view
    }

    private fun guardarJugador() {
        val nombre = binding.etNombre.text.toString().trim()
        val apellido = binding.etApellido.text.toString().trim()
        val fechaNacimiento = binding.etFechaNacimiento.text.toString().trim()
        val posicion = binding.etPosicion.text.toString().trim()
        val pieHabil = binding.spinnerPieHabil.selectedItem.toString()
        val equipoAnterior = binding.etEquipoAnterior.text.toString().trim()

        if (nombre.isEmpty() || apellido.isEmpty() || fechaNacimiento.isEmpty() || posicion.isEmpty() || equipoAnterior.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        sdf.isLenient = false
        val fechaDate = try {
            sdf.parse(fechaNacimiento)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Formato de fecha inválido, debe ser dd/MM/yyyy", Toast.LENGTH_SHORT).show()
            return
        }

        if (fechaDate == null) {
            Toast.makeText(requireContext(), "Fecha no válida", Toast.LENGTH_SHORT).show()
            return
        }

        val today = java.util.Calendar.getInstance()
        val birthDate = java.util.Calendar.getInstance().apply { time = fechaDate }

        var age = today.get(java.util.Calendar.YEAR) - birthDate.get(java.util.Calendar.YEAR)

        if (today.get(java.util.Calendar.DAY_OF_YEAR) < birthDate.get(java.util.Calendar.DAY_OF_YEAR)) {
            age--
        }

        val categoria = when (age) {
            in 5..7 -> "Prebenjamín"
            in 8..9 -> "Benjamín"
            in 10..11 -> "Alevín"
            in 12..13 -> "Infantil"
            in 14..15 -> "Cadete"
            in 16..18 -> "Juvenil"
            else -> "Senior"
        }

        val jugador = Jugador(
            nombre = nombre,
            apellido = apellido,
            fechaNacimiento = fechaNacimiento,
            posicion = posicion,
            pieHabil = pieHabil,
            equipoAnterior = equipoAnterior,
            categoria = categoria
        )

        jugadorViewModel.insertar(jugador)

        Toast.makeText(requireContext(), "Jugador guardado", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_nuevoJugador_to_jugadoresList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
