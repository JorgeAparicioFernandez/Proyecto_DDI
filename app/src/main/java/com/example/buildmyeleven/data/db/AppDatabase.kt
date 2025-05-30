package com.example.buildmyeleven.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.buildmyeleven.data.dao.JugadorDao
import com.example.buildmyeleven.data.model.Jugador

@Database(entities = [Jugador::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jugadorDao(): JugadorDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "jugadores_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
