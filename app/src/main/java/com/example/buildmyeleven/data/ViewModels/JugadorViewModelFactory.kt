package com.example.buildmyeleven.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buildmyeleven.data.JugadorRepository

class JugadorViewModelFactory(private val repository: JugadorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JugadorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JugadorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
