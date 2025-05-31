package com.example.buildmyeleven

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.buildmyeleven.databinding.LoginBinding

class FirstFragment : Fragment() {

    private var _binding: LoginBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bLogIn.setOnClickListener {

            val inputUsuario = binding.etUsuario.text.toString()
            val inputPassword = binding.edPassword.text.toString()

            val prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val savedUsuario = prefs.getString("usuario", "")
            val savedPassword = prefs.getString("password", "")

            if (inputUsuario == savedUsuario && inputPassword == savedPassword) {
                Toast.makeText(requireContext(), "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_FirstFragment_to_jugadores_list)

            } else {
                Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }


        }

        binding.bSingIn.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}