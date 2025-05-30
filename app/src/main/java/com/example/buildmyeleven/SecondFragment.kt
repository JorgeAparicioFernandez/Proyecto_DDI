package com.example.buildmyeleven

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.buildmyeleven.databinding.SinginBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: SinginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SinginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bRegistrarse.setOnClickListener{
            val usuario = binding.etUsuario.text.toString().trim()
            val correo = binding.etCorreo.text.toString().trim()
            val password = binding.edPassword.text.toString()
            val repetirPassword = binding.etRepetirPassword.text.toString()

            // Validaciones
            if (usuario.isBlank() || correo.isBlank() || password.isBlank() || repetirPassword.isBlank()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usuario.length < 4 || !usuario.matches(Regex("^[a-zA-Z0-9]+$"))) {
                Toast.makeText(requireContext(), "Usuario inválido. Debe tener al menos 4 caracteres y solo letras/números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(requireContext(), "Correo electrónico no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6 || !password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"))) {
                Toast.makeText(requireContext(), "Contraseña débil. Usa mínimo 6 caracteres con letras y números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repetirPassword) {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            prefs.edit().apply {
                putString("usuario", usuario)
                putString("correo", correo)
                putString("password", password)
                apply()
            }

            Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}