package com.example.buildmyeleven

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.buildmyeleven.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.FirstFragment -> binding.toolbar.visibility = View.GONE
                else -> binding.toolbar.visibility = View.VISIBLE
            }

            binding.toolbar.title = when (destination.id) {
                R.id.SecondFragment -> "Registro de Usuario"
                R.id.FirstFragment -> ""
                R.id.jugadoresList -> "Lista de Jugadores"
                R.id.DetalleJugador -> "Detalle del jugador"
                R.id.editarJugador ->  "Editar Jugador"
                else -> destination.label?.toString() ?: getString(R.string.app_name)
            }

            val showBackArrow = destination.id != R.id.jugadoresList
            supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
            supportActionBar?.setDisplayShowHomeEnabled(showBackArrow)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.FirstFragment)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
