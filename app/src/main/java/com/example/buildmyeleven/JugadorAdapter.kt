package com.example.buildmyeleven

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buildmyeleven.data.model.Jugador

class JugadorAdapter(
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder>() {

    private var jugadores: List<Jugador> = emptyList()

    fun setJugadores(jugadores: List<Jugador>) {
        this.jugadores = jugadores
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jugador, parent, false)
        return JugadorViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: JugadorViewHolder, position: Int) {
        holder.bind(jugadores[position])
    }

    override fun getItemCount(): Int = jugadores.size

    class JugadorViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var jugadorId: Int = 0
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val tvCategoria: TextView = itemView.findViewById(R.id.tvCategoria)

        init {
            itemView.setOnClickListener {
                onItemClick(jugadorId)
            }
        }

        fun bind(jugador: Jugador) {
            jugadorId = jugador.id
            tvNombre.text = "${jugador.nombre} ${jugador.apellido}"
            tvCategoria.text = jugador.categoria
        }
    }
}
